package ir.dunijet.studentManager.addStudent

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ir.dunijet.studentManager.model.Student
import ir.dunijet.studentManager.databinding.ActivityMain2Binding
import ir.dunijet.studentManager.model.MainRepository
import ir.dunijet.studentManager.util.asyncRequest
import ir.dunijet.studentManager.util.showToast

class AddStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var addStudentViewModel: AddStudentViewModel
    private val compositeDisposable = CompositeDisposable()
    private var isInserting = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMain2)
        addStudentViewModel = AddStudentViewModel(MainRepository())

        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.edtFirstName.requestFocus()


        val testMode = intent.getParcelableExtra<Student>("student")
        isInserting = (testMode == null)

        if (!isInserting) {
            logicUpdateStudent()
        }

        binding.btnDone.setOnClickListener {
            if (isInserting) {
                addNewStudent()
            } else {
                updateStudent()
            }
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    @SuppressLint("SetTextI18n")
    private fun logicUpdateStudent() {
        binding.btnDone.text = "update"

        val dataFromIntent = intent.getParcelableExtra<Student>("student")!!
        binding.edtScore.setText(dataFromIntent.score.toString())
        binding.edtCourse.setText(dataFromIntent.course)

        // split first name and last name from val name in server
        val splittedName = dataFromIntent.name.split(" ")

        binding.edtFirstName.setText(splittedName[0])
        binding.edtLastName.setText(splittedName[splittedName.size - 1])
    }
    private fun updateStudent() {
        val firstName = binding.edtFirstName.text.toString()
        val lastName = binding.edtLastName.text.toString()
        val course = binding.edtCourse.text.toString()
        val score = binding.edtScore.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && course.isNotEmpty() && score.isNotEmpty()) {

            addStudentViewModel.updateStudent(
                Student(firstName + "" + lastName, course, score.toInt()))
                .asyncRequest()
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {
                        showToast("update finished")
                        onBackPressed()
                    }

                    override fun onError(e: Throwable) {
                        showToast("error -> ${e.message ?: "null"}")
                    }
                })

            Toast.makeText(this, "Update Finished", Toast.LENGTH_SHORT).show()
            onBackPressed()
        } else {
            showToast("Enter Fields")
        }
    }
    private fun addNewStudent() {
        val firstName = binding.edtFirstName.text.toString()
        val lastName = binding.edtLastName.text.toString()
        val course = binding.edtCourse.text.toString()
        val score = binding.edtScore.text.toString()

        if (firstName.isNotEmpty() && lastName.isNotEmpty() && course.isNotEmpty() && score.isNotEmpty()) {
            addStudentViewModel.insertNewStudent(
                Student(firstName + "" + lastName, course, score.toInt())
            )
                .asyncRequest()
                .subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onComplete() {
                        showToast("student inserted")
                        onBackPressed()
                    }

                    override fun onError(e: Throwable) {
                        showToast("error -> ${e.message ?: "null"}")
                    }
                })
        } else {
            showToast("Enter Fields")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }

}