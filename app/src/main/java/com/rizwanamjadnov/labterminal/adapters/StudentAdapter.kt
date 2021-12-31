package com.rizwanamjadnov.labterminal.adapters

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rizwanamjadnov.labterminal.R
import com.rizwanamjadnov.labterminal.databinding.ActivityMainBinding.inflate
import com.rizwanamjadnov.labterminal.models.Student
import com.rizwanamjadnov.labterminal.utils.SMSSender

class StudentAdapter(private val context: Context, private val data: List<Student>):
    RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
        inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val studentNameItem: TextView = itemView.findViewById(R.id.studentNameItem)
            val studentRegistrationNumberItem: TextView = itemView.findViewById(R.id.studentRegistrationNumberItem)
            val studentPhoneNumberItem: TextView = itemView.findViewById(R.id.studentPhoneNumberItem)
            val sendSMSButton: Button = itemView.findViewById(R.id.sendSMSButton)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.student_list_item, null, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = data[position]

        val sendSMSDialog = AlertDialog.Builder(context)
            .setView(R.layout.send_sms_dialog)
            .create()


        holder.apply {
            studentNameItem.text = student.name
            studentRegistrationNumberItem.text = student.registrationNumber
            studentPhoneNumberItem.text = student.phoneNumber
            sendSMSButton.setOnClickListener{
                sendSMSDialog.show()


                val smsText = sendSMSDialog.findViewById<EditText>(R.id.smsEditText)
                Log.d("SMS", smsText.text.toString())
                sendSMSDialog.findViewById<Button>(R.id.sendButton)
                    .setOnClickListener {
                        SMSSender.send(student.phoneNumber, smsText.text.toString())
                        smsText.text.clear()
                        sendSMSDialog.hide()
                    }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}