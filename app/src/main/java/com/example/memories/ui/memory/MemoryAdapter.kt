import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.memories.databinding.LayoutMemoryItemBinding
import com.example.memories.model.Memory
import com.example.memories.utils.DiffUtilCallback


class MemoryAdapter(private val onClicked: (Memory) -> Unit) :
    ListAdapter<Memory, MemoryAdapter.ViewHolder>
        (DiffUtilCallback<Memory>())
{


    inner class ViewHolder(private val binding: LayoutMemoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)
    {

        fun bind(item: Memory?, position: Int)
        {

            //binding.layoutMemoryItemTextViewDescription.text = item?.description


            if (item?.imageUrl is String)
                binding.layoutMemoryItemImageView.load(item?.imageUrl.toString())
            else binding.layoutMemoryItemImageView.setImageResource(item?.imageUrl as Int)

            binding.root.setOnClickListener()
            {
                item?.let { it1 -> onClicked(it1) }
            }

        } // bind closed

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view =
            LayoutMemoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.bind(getItem(position), position)

    } // onBindViewHolder closed




}    