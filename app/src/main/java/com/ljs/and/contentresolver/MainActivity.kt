package com.ljs.and.contentresolver


import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ljs.and.contentresolver.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        requirePermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 999)


    }

    override fun permissionGranted(requestCode: Int) {

        startProcess()

    }

    override fun permissionDenied(requestCode: Int) {

        Toast.makeText(this,
            "외부 저장소 권한 승인이 필요합니다. 앱을 종료합니다.",
            Toast.LENGTH_LONG).show()

        finish()

    }

    fun startProcess(){

        val adapter = MusicRecyclerAdapter()
        adapter.musicList.addAll(getMusicList())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }

    fun getMusicList(): List<Music>{
        val listUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val proj = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION,
        )
        val cursor = contentResolver.query(listUri, proj, null, null, null)

        val musicList = mutableListOf<Music>()

        while (cursor?.moveToNext() == true ){
            val id = cursor.getString(0)
            val title = cursor.getString(1)
            val artist = cursor.getString(2)
            val albumId = cursor.getString(3)
            val duration = cursor.getLong(4)

            val music = Music(id, title, artist, albumId, duration)
            musicList.add(music)
        }

        return musicList
    }
}