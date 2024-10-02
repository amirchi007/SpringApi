package ir.dunijet.studentManager.mainScreen

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import ir.dunijet.studentManager.model.MainRepository
import ir.dunijet.studentManager.model.Student
import java.util.concurrent.TimeUnit

class MainScreenViewModel(
    private val mainRepository: MainRepository
) {
    val progressBarSubject = BehaviorSubject.create<Boolean>()

    fun getAllStudents(): Single<ArrayList<Student>> {

        progressBarSubject.onNext(true)
        return mainRepository
            .getAllStudents()
            .delay(2,TimeUnit.SECONDS)
            .doFinally { progressBarSubject.onNext(false) }
    }

    fun removeStudent(studentName: String): Completable {
        return mainRepository.removeStudent(studentName)
    }
}