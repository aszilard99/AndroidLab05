package com.example.lab05.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.NonNull
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lab05.QuizController
import com.example.lab05.R
import com.example.lab05.models.MyViewModel
import com.example.lab05.models.Question

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var quizController : QuizController
    lateinit var questionTextView : TextView
    lateinit var rButton0 : RadioButton
    lateinit var rButton1 : RadioButton
    lateinit var rButton2 : RadioButton
    lateinit var rButton3 : RadioButton
    lateinit var nextQuestionButton : Button
    lateinit var question: Question

    private val myViewModel : MyViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question, container, false)
        view?.apply {
            initializeQuestionFragment(this)
        }
        return view
    }

    private fun initializeQuestionFragment(view : View) {
        quizController = QuizController(view)
        myViewModel.setNumOfTotalAnswers(quizController.totalAnswerNum)
        Log.d("totalAnswerNum : ", "${quizController.totalAnswerNum}")
        questionTextView = view.findViewById(R.id.questionTextView)
        rButton0 = view.findViewById(R.id.button0)
        rButton1 = view.findViewById(R.id.button1)
        rButton2 = view.findViewById(R.id.button2)
        rButton3 = view.findViewById(R.id.button3)

        nextQuestionButton = view.findViewById(R.id.nextQuestionButton)

        val tempQuestion = quizController.nextQuestion()
        if (tempQuestion == null){
            startQuizEndFragment()
        }
        else {
            question = tempQuestion
            questionTextView.text = question.text
            rButton0.text = question.answers.get(0).answer
            rButton1.text = question.answers.get(1).answer
            rButton2.text = question.answers.get(2).answer
            rButton3.text = question.answers.get(3).answer
            nextQuestionButton.visibility = View.INVISIBLE

            rButton0.setOnClickListener { onRButtonClick(rButton0) }
            rButton1.setOnClickListener { onRButtonClick(rButton1) }
            rButton2.setOnClickListener { onRButtonClick(rButton2) }
            rButton3.setOnClickListener { onRButtonClick(rButton3) }
            nextQuestionButton.setOnClickListener { showNextQuestion() }
        }

    }

    private fun showNextQuestion(){
        evaluateAnswer()
        val question = quizController.nextQuestion()
        if (question == null){
            startQuizEndFragment()
        }
        else {
            questionTextView.text = question.text
            rButton0.text = question.answers.get(0).answer
            rButton1.text = question.answers.get(1).answer
            rButton2.text = question.answers.get(2).answer
            rButton3.text = question.answers.get(3).answer
        }
    }
    private fun evaluateAnswer() {
        //will store which answer was selected
        var answerNumber = -1
        if (rButton0.isChecked){
            answerNumber = 0
        }
        if (rButton1.isChecked){
            answerNumber = 1
        }
        if (rButton2.isChecked){
            answerNumber = 2
        }
        if (rButton3.isChecked){
            answerNumber = 3
        }
        if (question.answers.get(answerNumber).isValid){
            Log.d("answer validity", "correct")

            myViewModel.incrementNumOfCorrectAnswers()
        }
        else{
            Log.d("answer validity", "incorrect")

        }
    }
    /***
    Makes sure that the player can only go to the next question if he/she already selected one answer on the current question
     ***/
    private fun onRButtonClick(v : RadioButton){
        if (nextQuestionButton.isVisible && !v.isChecked){
            nextQuestionButton.visibility = View.INVISIBLE
        }
        else {
            nextQuestionButton.visibility = View.VISIBLE
        }
    }

    private fun startQuizEndFragment(){
        findNavController().navigate(R.id.action_questionFragment_to_quizEndFragment)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}