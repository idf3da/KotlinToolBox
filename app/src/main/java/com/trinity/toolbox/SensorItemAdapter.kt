package com.trinity.toolbox

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class SensorItemAdapter(private val context: Activity, private val data: List<Sensor>) : BaseAdapter() {

    private inner class ViewHolder {
        var tv1: TextView? = null
        var tv2: TextView? = null
        var tv3: TextView? = null
        var tv4: TextView? = null
        var tv5: TextView? = null
        var tv6: TextView? = null
    }

    override fun getView(position: Int, _convertView: View?, parent: ViewGroup?): View {
        var convertView = _convertView
        val holder: ViewHolder
        if(convertView == null){
            holder = ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.card_sensor, null, true)
            holder.tv1 = convertView!!.findViewById(R.id.tv1) as TextView
            holder.tv2 = convertView.findViewById(R.id.tv2) as TextView
            holder.tv3 = convertView.findViewById(R.id.tv3) as TextView
            holder.tv4 = convertView.findViewById(R.id.tv4) as TextView
            holder.tv5 = convertView.findViewById(R.id.tv5) as TextView
            holder.tv6 = convertView.findViewById(R.id.tv6) as TextView

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        holder.tv1!!.text = data[position].name
        holder.tv2!!.text = "Version: ${data[position].version}"
        holder.tv3!!.text = "Vendor: ${data[position].vendor}"
        holder.tv4!!.text = "Range: ${data[position].maximumRange}"
        holder.tv5!!.text = "Delay: ${data[position].minDelay}"
        holder.tv6!!.text = "Resolution: ${data[position].resolution}"
        return convertView
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return data.size
    }
}
