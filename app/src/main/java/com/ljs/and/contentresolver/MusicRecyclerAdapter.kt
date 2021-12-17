package com.ljs.and.contentresolver


import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ljs.and.contentresolver.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class MusicRecyclerAdapter: RecyclerView.Adapter<Holder>() {

    var musicList = mutableListOf<Music>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val music = musicList.get(position)
        holder.setMusic(music)
    }

    override fun getItemCount(): Int {
        return musicList.size
    }
}

class Holder(val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root){

    var musicUri: Uri? = null

    fun setMusic(music: Music){
        binding.run { // run 함수를 사용하면 매번binding.을 입력하지 않아도 된다.
            imageAlbum.setImageURI(music.getAlbumUri())
            textArtist.text = music.artist
            textTitle.text = music.title

            val duration = SimpleDateFormat("mm:ss").format(music.duration)
            textDuration.text = duration
        }
        this.musicUri = music.getMusicUri()
    }

}