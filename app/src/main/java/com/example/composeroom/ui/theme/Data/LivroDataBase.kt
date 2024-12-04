package com.example.composeroom.ui.theme.Data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


    @Database(entities = [Livro::class], version = 1, exportSchema = false)
    abstract class LivroDatabase: RoomDatabase() {

        abstract fun livroDao(): LivroDao

        companion object{

            @Volatile
            private var INSTANCE: LivroDatabase? = null

            /*
            * start(){
            *   if (INSTANCE != null){
            *       -----
            *   } else{
            *       INSTANCE = new LivroDatabase()
            *   }
            *
            *   return INSTANCE;
            *
            * }
            *
            * */


            fun getDatabase(context: Context): LivroDatabase {

                val tempInstance = INSTANCE

                if(tempInstance != null){
                    return tempInstance
                }else{

                    synchronized(this){

                        val instance = Room.databaseBuilder(
                            context.applicationContext,
                            LivroDatabase::class.java,
                            "livro_table"
                        ).build()

                        INSTANCE = instance
                        return instance

                    }

                }

            }



        }


    }

