package ru.skillbranch.devintensive

import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.models.Bender
import ru.skillbranch.devintensive.models.Bender.Question

class MainActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView
    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        makeSendOnActionDone(messageEt)
        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status),Bender.Question.valueOf(question))

        Log.d("M_MainActivity","onCreate $status $question")
        var (r,g,b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b),PorterDuff.Mode.MULTIPLY)


        textTxt.text = benderObj.askQuestion()

        messageEt.setOnEditorActionListener{ _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                sendAnswer()
                true
            } else {
                false
            }
        }
        sendBtn.setOnClickListener{
            sendAnswer()
        }

    }

    private fun makeSendOnActionDone(editText: EditText) {
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        editText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) sendBtn.performClick()
            false
        }
    }
    override fun onClick(v: View?) {
        if (v?.id == R.id.iv_send)
            if (isAnswerValid())
                sendAnswer()
            else makeErrorMessage()
    }

    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity","onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity","onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("M_MainActivity","OnResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity","Pause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity","Destroy")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        
        outState?.putString("STATUS", benderObj.status.name)
        outState?.putString("QUESTION", benderObj.question.name)
        Log.d("M_MainActivity","onSaveInstanceState ${benderObj.status.name}  ${benderObj.question.name}")
    }

    private fun isAnswerValid(): Boolean {
        return benderObj.question.validate(messageEt.text.toString())
    }


    private fun sendAnswer(){
        var (phrase, color) = benderObj.listenAnswer(messageEt.text.toString().toLowerCase())
        messageEt.setText("")
        var (r,g,b) = color
        benderImage.setColorFilter(Color.rgb(r,g,b),PorterDuff.Mode.MULTIPLY)
        textTxt.text = phrase
    }

    private fun makeErrorMessage() {
        val errorMessage = when(benderObj.question){
            Question.NAME -> "Имя должно начинаться с заглавной буквы"
            Question.PROFESSION -> "Профессия должна начинаться со строчной буквы"
            Question.MATERIAL -> "Материал не должен содержать цифр"
            Question.BDAY -> "Год моего рождения должен содержать только цифры"
            Question.SERIAL -> "Серийный номер содержит только цифры, и их 7"
            else -> "На этом все, вопросов больше нет"
        }
        textTxt.text = errorMessage + "\n" + benderObj.question.question
        messageEt.setText("")
    }
}
