package br.com.dio.app.repositories.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.app.repositories.data.model.Repo
import br.com.dio.app.repositories.databinding.ItemRepoBinding
import com.bumptech.glide.Glide


class RepoListAdapter : ListAdapter<Repo, RepoListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ViewHolder(
        private val binding: ItemRepoBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Repo) {
            binding.tvRepoName.text = item.name
            binding.tvRepoDescription.text = item.description
            binding.chipStarCount.text = item.stargazersCount.toString()
            binding.tvRepoLanguage.text = item.language
            binding.btOpenLink.setOnClickListener {
                val context: Context = ViewHolder(ItemRepoBinding.bind(itemView)).itemView.context
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(item.htmlURL)
                startActivity(context, i, null)
            }

            Glide.with(binding.root.context).load(item.owner.avatarURL).into(binding.ivIcon)
            }
        }
    }

class DiffCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo) =
        oldItem.id == newItem.id
}