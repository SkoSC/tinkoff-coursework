package com.skosc.tkffintech.usecase

import com.skosc.tkffintech.entities.Homework
import com.skosc.tkffintech.model.repo.CourseRepo
import com.skosc.tkffintech.model.repo.CurrentUserRepo
import com.skosc.tkffintech.model.repo.HomeworkRepo
import com.skosc.tkffintech.viewmodel.UserWithGradesSum
import io.reactivex.Observable

class LoadGrades(
        private val homeworkRepo: HomeworkRepo,
        private val courseRepo: CourseRepo,
        private val currentUserRepo: CurrentUserRepo
) {

    fun loadGrades(userId: Long, course: String): Observable<List<Pair<Homework, List<TaskWithGrade>>>> {
        return homeworkRepo.gradesWithHomework(userId, course)
    }

    fun loadGradesForCurrentUser(course: String): Observable<List<Pair<Homework, List<TaskWithGrade>>>> {
        return loadGrades(currentUserRepo.idBlocking!!, course)
    }

    fun loadGradesSumForAllCourse(course: String): Observable<List<UserWithGradesSum>> {
        return homeworkRepo.gradesTotalForAllUsersWithCourse(course)
    }

    fun checkForUpdate() {
        courseRepo.performSoftUpdate()
        homeworkRepo.performSoftUpdate()
    }

}