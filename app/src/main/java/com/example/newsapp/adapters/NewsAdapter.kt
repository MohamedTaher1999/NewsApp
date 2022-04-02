package com.example.newsapp.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.models.Article
import org.w3c.dom.Text

class NewsAdapter(list:List<Article>) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {


    inner class ArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val articleImage:ImageView=itemView.findViewById(R.id.ivArticleImage);
        val articleTitle: TextView =itemView.findViewById(R.id.tvTitle);
        val articleDescription:TextView=itemView.findViewById(R.id.tvDescription)
        val articleSource:TextView=itemView.findViewById(R.id.tvSource)
        val articlePublishedDate:TextView=itemView.findViewById(R.id.tvPublishedAt)
    }

    private val differCallback=object :DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
         return ArticleViewHolder(
             LayoutInflater.from(parent.context).inflate(
                 R.layout.item_article_preview,
                 parent,
                 false
             )
         )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article=differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(holder.articleImage)
            holder.articleSource.text=article.source.name
            holder.articleDescription.text=article.description
            holder.articleTitle.text=article.title
            holder.articlePublishedDate.text=article.publishedAt
            onItemClickListener?.let {
                it(article)
            }
        }
    }

    override fun getItemCount()=differ.currentList.size

    private var onItemClickListener:((Article)->Unit)?=null


    fun setOnItemClickListener(listener:(Article)->Unit){
        onItemClickListener=listener
    }
}