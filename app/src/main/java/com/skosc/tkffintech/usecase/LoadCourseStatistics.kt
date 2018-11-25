package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.CourseStatistics
import com.skosc.tkffintech.entities.HomeworkStatus
import com.skosc.tkffintech.entities.HomeworkTaskType
import com.skosc.tkffintech.entities.composite.HomeworkWithGrades
import com.skosc.tkffintech.entities.composite.UserWithGradesSum
import com.skosc.tkffintech.misc.Ratio
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.HomeworkRepo
import io.reactivex.Single

class LoadCourseStatistics(private val currentUserRepo: CurrentUserRepo, private val homeworkRepo: HomeworkRepo) {
    fun gradeSumForUserOnCourse(course: String): Single<List<UserWithGradesSum>> {
        return homeworkRepo.gradesSum(course)
    }

    fun bundled(course: String): Single<CourseStatistics> {
        val user = currentUserRepo.idBlocking!!
        return homeworkRepo.grades(user, course).map(this::calculateStatistics)
    }

    private fun calculateStatistics(data: List<HomeworkWithGrades>): CourseStatistics {

        val homeworkRatio = calculateHomeworkRatio(data)
        val testRatio = calculateTestsRatio(data)
        val scoreRatio = calculateScoreRatio(data)
        val completionRatio = calculateCoalitionRatio(homeworkRatio, testRatio, scoreRatio)

        return CourseStatistics(
                homeworkRatio = homeworkRatio,
                testRatio = testRatio,
                scoreRatio = scoreRatio,
                completionRatio = completionRatio)
    }

    private fun calculateCoalitionRatio(homeworkRatio: Ratio, testRatio: Ratio, scoreRatio: Ratio): Ratio {
        return (homeworkRatio mean testRatio) mean scoreRatio
    }

    private fun calculateScoreRatio(data: List<HomeworkWithGrades>): Ratio {
        val score = data.flatMap { it.grades }
        val totalScore = score.map { it.first.maxScore.toDouble() }.sum()
        val userScore = score.map { it.second.mark.toDouble() }.sum()
        return Ratio(userScore, totalScore)
    }

    private fun calculateTestsRatio(data: List<HomeworkWithGrades>): Ratio {
        val tests = data
                .flatMap { it.grades }
                .filter { it.first.taskType == HomeworkTaskType.TEST }
        val totalTests = tests.size
        val completedTests = tests
                .map { it.second }
                .filter { it.status == HomeworkStatus.ACCEPTED }
                .size
        return Ratio(completedTests, totalTests)
    }

    private fun calculateHomeworkRatio(data: List<HomeworkWithGrades>): Ratio {
        val groupedHomeworks = data.groupBy { it.homework }
        val totalHomeworks = groupedHomeworks.size
        val completedHomeworks = groupedHomeworks.values
                .map {
                    it.flatMap { it.grades }
                            .map { it.second }
                            .all { it.status == HomeworkStatus.ACCEPTED }
                }.filter { it }.count()
        return Ratio(completedHomeworks, totalHomeworks)
    }
}