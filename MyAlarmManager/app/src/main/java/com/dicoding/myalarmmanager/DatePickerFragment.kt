package com.dicoding.myalarmmanager

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var mListener: DialogDateListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as? DialogDateListener
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, date)
    }

    interface DialogDateListener {
        fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        mListener?.onDialogDateSet(tag, year, month, dayOfMonth)
    }
}