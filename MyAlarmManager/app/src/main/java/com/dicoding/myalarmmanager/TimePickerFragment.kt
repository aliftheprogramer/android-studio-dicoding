package com.dicoding.myalarmmanager

import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private var mListener: TimePickerDialogListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as? TimePickerDialogListener
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(requireContext(), this, hour, minute, true)
    }

    interface TimePickerDialogListener {
        fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        mListener?.onDialogTimeSet(tag, hourOfDay, minute)
    }
}