package ir.dunijet.studentManager.model

import io.reactivex.Completable
import io.reactivex.Single
import ir.dunijet.studentManager.util.BASE_URL
import ir.dunijet.studentManager.util.studentTOJsonObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainRepository {
    private val apiService: ApiService

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    fun getAllStudents(): Single<ArrayList<Student>> {
        return apiService.getAllStudents()
    }

    fun insertStudents(student: Student): Completable {
        return apiService.insertStudent(studentTOJsonObject(student))
    }

    fun updateStudent(student: Student): Completable {
        return apiService.updateStudent(student.name, studentTOJsonObject(student))
    }

    fun removeStudent(studentName: String): Completable {
        return apiService.deleteStudent(studentName)
    }
}