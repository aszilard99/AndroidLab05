package com.example.lab05.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.lab05.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizStart.newInstance] factory method to
 * create an instance of this fragment.
 */

class QuizStart : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var startButton: Button
    lateinit var playerNameTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    // a fragmenteknek saját viselkedést adtam a back gomb megnyomásakor, és
    // valószínűleg a back stacken emiatt kavarodás van és a home fragmentből
    // enélkül nem lépne ki rendesen back gombbal
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(
            true // default to enabled
        ) {
            override fun handleOnBackPressed() {
                //lezárja az activityt -> az appot is mert 1 activityből áll
                activity?.finishAndRemoveTask()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,  // LifecycleOwner
            callback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_quiz_start, container, false)
        view?.apply {
            initializeView(this)
            registerListeners(this)
        }
        return view
    }

    private fun registerListeners(view: View){
        startButton.setOnClickListener{
            //Log.i(ContentValues.TAG, playerName.text.toString())
            findNavController().navigate(R.id.action_quizStart_to_questionFragment)
        }
    }

    private fun initializeView(view: View){
        playerNameTextView = view.findViewById(R.id.editTextTextPersonName)
        startButton = view.findViewById(R.id.start_button)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuizStart.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuizStart().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}