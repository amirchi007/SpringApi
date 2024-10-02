package ir.dunijet.studentManager.addStudent

import io.reactivex.Completable
import ir.dunijet.studentManager.model.MainRepository
import ir.dunijet.studentManager.model.Student

class AddStudentViewModel(
    private val mainRepository: MainRepository
) {
    fun insertNewStudent(student: Student):Completable {
        return mainRepository.insertStudents(student)
    }

    fun updateStudent(student: Student): Completable {
        return mainRepository.updateStudent(student)
    }

}