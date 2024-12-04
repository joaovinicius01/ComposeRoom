package com.example.composeroom.ui.theme.Data

    import androidx.room.Dao
    import androidx.room.Delete
    import androidx.room.Insert
    import androidx.room.OnConflictStrategy
    import androidx.room.Query
    import androidx.room.Update

    @Dao
    interface LivroDao {

        /*
        * meuLivro = new Livro()
        * meuLivro.nome = "Marvel"
        * meuLivro.ano = 2022
        *
        * LivroDao.addLivro(meuLivro)
        * */

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun addLivro(livro: Livro)

        @Update
        suspend fun atualizarLivro(livro: Livro)

        @Delete
        suspend fun deletarLivro(livro: Livro)

        @Query("SELECT * FROM livro_table")
        suspend fun listarLivros() : List<Livro>

        @Query("SELECT * FROM livro_table ORDER BY ano ASC")
        suspend fun listarLivrosEmOrdem() : List<Livro>

        @Query("SELECT * FROM livro_table WHERE id = :livroId LIMIT 1")
        suspend fun getLivroById(livroId: Int): Livro?

    }