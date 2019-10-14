package com.example.parkmobile

import android.util.Log
import com.example.parkmobile.DataClass.*
import com.example.parkmobile.DataClass.Transactions.AccountTransactions
import com.example.parkmobile.Fragments.ProfileFragment
import com.example.parkmobile.Retrofit.ApiV2Interface
import com.example.parkmobile.Retrofit.ParkchainInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_profile.*
import org.bitcoinj.wallet.Wallet
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    companion object{
        lateinit var arrayList: ArrayList<Parkirisce>

        @BeforeClass @JvmStatic fun setup() {
            arrayList = arrayListOf(
                Parkirisce("0", 1.9, "1", "Luka", "ax649ef4g9rhg64", 6.55556, 15.5568, "Zofke Kvedrove 2b", "Pred hišo", 5, 2),
                Parkirisce("1", 3.5, "2", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Okoli hiše", 15, 7),
                Parkirisce("2", 2.0, "3", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "A garažo", 25, 7),
                Parkirisce("3", 1.5, "4", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Ga blokom", 65, 7),
                Parkirisce("4", 2.3, "5", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Sa gostilno", 25, 7),
                Parkirisce("5", 5.5, "7", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za hišo", 11, 7),
                Parkirisce("6", 1.2, "8", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za avtom", 56, 7),
                Parkirisce("7", 2.2, "9", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za blokom", 19, 7),
                Parkirisce("8", 3.4, "10", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za igriščem", 18, 7)
            )
        }
    }
    @Test
    fun `sortiranje po abecedi`() {
        val pravilnoSortiranList = arrayListOf(
            Parkirisce("2", 2.0, "3", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "A garažo", 25, 7),
            Parkirisce("3", 1.5, "4", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Ga blokom", 65, 7),
            Parkirisce("1", 3.5, "2", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Okoli hiše", 15, 7),
            Parkirisce("0", 1.9, "1", "Luka", "ax649ef4g9rhg64", 6.55556, 15.5568, "Zofke Kvedrove 2b", "Pred hišo", 5, 2),
            Parkirisce("4", 2.3, "5", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Sa gostilno", 25, 7),
            Parkirisce("6", 1.2, "8", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za avtom", 56, 7),
            Parkirisce("7", 2.2, "9", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za blokom", 19, 7),
            Parkirisce("5", 5.5, "7", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za hišo", 11, 7),
            Parkirisce("8", 3.4, "10", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za igriščem", 18, 7)
        )
        assertEquals(pravilnoSortiranList, arrayList.sortedWith(NazivComparator))
    }
    @Test
    fun `sortiranje po ceni`() {
        val pravilnoSortiranList = arrayListOf(
            Parkirisce("6", 1.2, "8", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za avtom", 56, 7),
            Parkirisce("3", 1.5, "4", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Ga blokom", 65, 7),
            Parkirisce("0", 1.9, "1", "Luka", "ax649ef4g9rhg64", 6.55556, 15.5568, "Zofke Kvedrove 2b", "Pred hišo", 5, 2),
            Parkirisce("2", 2.0, "3", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "A garažo", 25, 7),
            Parkirisce("7", 2.2, "9", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za blokom", 19, 7),
            Parkirisce("4", 2.3, "5", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Sa gostilno", 25, 7),
            Parkirisce("8", 3.4, "10", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za igriščem", 18, 7),
            Parkirisce("1", 3.5, "2", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Okoli hiše", 15, 7),
            Parkirisce("5", 5.5, "7", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za hišo", 11, 7)
        )
        assertEquals(pravilnoSortiranList, arrayList.sortedWith(CenaComparator))
    }

    @Test
    fun `sortiranje po mestih`(){
        val pravilnoSortiranList = arrayListOf(
            Parkirisce("3", 1.5, "4", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Ga blokom", 65, 7),
            Parkirisce("6", 1.2, "8", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za avtom", 56, 7),
            Parkirisce("2", 2.0, "3", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "A garažo", 25, 7),
            Parkirisce("4", 2.3, "5", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Sa gostilno", 25, 7),
            Parkirisce("7", 2.2, "9", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za blokom", 19, 7),
            Parkirisce("8", 3.4, "10", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za igriščem", 18, 7),
            Parkirisce("1", 3.5, "2", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Okoli hiše", 15, 7),
            Parkirisce("5", 5.5, "7", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za hišo", 11, 7),
            Parkirisce("0", 1.9, "1", "Luka", "ax649ef4g9rhg64", 6.55556, 15.5568, "Zofke Kvedrove 2b", "Pred hišo", 5, 2)
        )
        assertEquals(pravilnoSortiranList, arrayList.sortedWith(MestaComparator))
    }

    @Test
    fun `sortiranje po oddaljenosti`(){
        val parkirisce1 = Parkirisce("0", 1.9, "1", "Luka", "ax649ef4g9rhg64", 6.55556, 15.5568, "Zofke Kvedrove 2b", "Pred hišo", 5, 2)
        val parkirisce2 = Parkirisce("1", 3.5, "2", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Okoli hiše", 15, 7)
        val parkirisce3 = Parkirisce("2", 2.0, "3", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "A garažo", 25, 7)
        val parkirisce4 = Parkirisce("3", 1.5, "4", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Ga blokom", 65, 7)
        val parkirisce5 = Parkirisce("4", 2.3, "5", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Sa gostilno", 25, 7)
        val parkirisce6 = Parkirisce("5", 5.5, "6", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za hišo", 11, 7)
        val parkirisce7 = Parkirisce("6", 1.2, "7", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za avtom", 56, 7)
        val parkirisce8 = Parkirisce("7", 2.2, "8", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za blokom", 19, 7)
        val parkirisce9 = Parkirisce("8", 3.4, "9", "Luka", "ax649ef4g9rhg64",46.57956, 15.5598, "Maksima gorkega 15", "Za igriščem", 18, 7)
        parkirisce1.oddaljenost = 50.0
        parkirisce2.oddaljenost = 56.0
        parkirisce3.oddaljenost = 97.6
        parkirisce4.oddaljenost = 113.9
        parkirisce5.oddaljenost = 114.0
        parkirisce6.oddaljenost = 130.5
        parkirisce7.oddaljenost = 260.0
        parkirisce8.oddaljenost = 508.7
        parkirisce9.oddaljenost = 1246.5
        val pravilnoSortiranList = arrayListOf(parkirisce1, parkirisce2, parkirisce3, parkirisce4, parkirisce5, parkirisce6, parkirisce7, parkirisce8, parkirisce9)
        val napacnoSortiranList = arrayListOf(parkirisce5, parkirisce3, parkirisce1, parkirisce2, parkirisce4, parkirisce9, parkirisce7, parkirisce8, parkirisce6)


        assertEquals(pravilnoSortiranList, napacnoSortiranList.sortedWith(OddaljenostComparator))
    }

    @Test
    fun `retrofit klic za parkirisca ni null`(){
        ParkchainInterface.create().vrniVsaParkirisca()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    assertNotNull(result)
                },
                { error ->
                    error.printStackTrace()
                }
            )
    }

    @Test
    fun `retrofit klic za parkirisca vrne pravilni razred`(){
        ParkchainInterface.create().vrniVsaParkirisca()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    val resultArray = ArrayList(result)
                    assertEquals(Parkirisce::class, resultArray[0]::class)
                },
                { error ->
                    error.printStackTrace()
                }
            )
    }

    @Test
    fun `retrofit klic za denarnico ni null`(){
        ApiV2Interface.create().vrniDenarnico("PS4ySsmTU4yTyyFfWQBFi59ENyQbpzBUPj")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    assertNotNull(result)
                },
                { error ->
                    error.printStackTrace()
                }
            )
    }

    @Test
    fun `retrofit klic za denarnico vrne pravilni razred`(){
        ApiV2Interface.create().vrniDenarnico("PS4ySsmTU4yTyyFfWQBFi59ENyQbpzBUPj")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    assertEquals(Wallet::class, result::class)
                },
                { error ->
                    error.printStackTrace()
                }
            )
    }

    @Test
    fun `retrofit klic za transakcije ni null`(){
        ApiV2Interface.create().vrniDenarnico("PS4ySsmTU4yTyyFfWQBFi59ENyQbpzBUPj")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    assertNotNull(result)
                },
                { error ->
                    error.printStackTrace()
                }
            )
    }

    @Test
    fun `retrofit klic za transakcije vrne pravilni razred`(){
        ApiV2Interface.create().vrniTransakcije("PS4ySsmTU4yTyyFfWQBFi59ENyQbpzBUPj")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    assertEquals(AccountTransactions::class, result.data[0]::class)
                },
                { error ->
                    error.printStackTrace()
                }
            )
    }


}
