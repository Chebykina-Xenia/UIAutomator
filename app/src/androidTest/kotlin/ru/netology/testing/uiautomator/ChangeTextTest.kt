package ru.netology.testing.uiautomator

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


const val SETTINGS_PACKAGE = "com.android.settings"
const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"

const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class ChangeTextTest {

    private lateinit var device: UiDevice
    private val textToSet = "Netology"

//    @Test
//    fun testInternetSettings() {
//        // Press home
//        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//        device.pressHome()
//
//        // Wait for launcher
//        val launcherPackage = device.launcherPackageName
//        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
//        waitForPackage(SETTINGS_PACKAGE)
//
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val intent = context.packageManager.getLaunchIntentForPackage(SETTINGS_PACKAGE)
//        context.startActivity(intent)
//        device.wait(Until.hasObject(By.pkg(SETTINGS_PACKAGE)), TIMEOUT)
//
//        device.findObject(
//            UiSelector().resourceId("android:id/title").instance(0)
//        ).click()
//    }

    //    @Test
//    fun testChangeText() {
//        // Press home
//        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//        device.pressHome()
//
//        // Wait for launcher
//        val launcherPackage = device.launcherPackageName
//        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
//        waitForPackage(SETTINGS_PACKAGE)
//
//        val context = ApplicationProvider.getApplicationContext<Context>()
//        val packageName = context.packageName
//        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
//        context.startActivity(intent)
//        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
//
//
//        device.findObject(By.res(packageName, "userInput")).text = textToSet
//        device.findObject(By.res(packageName, "buttonChange")).click()
//
//        val result = device.findObject(By.res(packageName, "textToBeChanged")).text
//        assertEquals(result, textToSet)
//    }
    //функция для запуска приложения
    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }

    @Before
    fun beforeEachTest() {
        // Press home — нажимаем на кнопку домой, на случай если у нас открыто какое-то приложение
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        // Wait for launcher
        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
    }

    @Test
    fun testInternetSettings() {
        waitForPackage(SETTINGS_PACKAGE)

        device.findObject(
            UiSelector().resourceId("android:id/title").instance(0)
        ).click()
    }

    //проверка изменения текста
    @Test
    fun testChangeText() {
        //запускаем приложение
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)
        //вводим текст в поле
        device.findObject(By.res(packageName, "userInput")).text = textToSet
        //кликаем по кнопке
        device.findObject(By.res(packageName, "buttonChange")).click()

        //выводим фактический результат
        val result = device.findObject(By.res(packageName, "textToBeChanged")).text
        //проверяем фактический и ожидаемый результат
        assertEquals(result, textToSet)
    }

    //Тест на попытку установки пустой строки
    @Test
    fun testEmptyLine() {

        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        //получаем значение из верхней строки
        val expectedText = device.findObject(By.res(packageName, "textToBeChanged")).text
        //вводим пустую строку
        device.findObject(By.res(packageName, "userInput")).text = " "
        //кликаем по кнопке
        device.findObject(By.res(packageName, "buttonChange")).click()
        //получаем фактический результат
        val resultText = device.findObject(By.res(packageName, "textToBeChanged")).text
        assertEquals(resultText, expectedText)
    }

    //Тест на открытие текста в новой Activity
    @Test
    fun testOpenActivity() {

        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        //вводим значение в поле
        device.findObject(By.res(packageName, "userInput")).text = textToSet
        //кликаем по кнопке
        device.findObject(By.res(packageName, "buttonActivity")).click()
        //ожидаем открытие второй страницы
        waitForPackage(MODEL_PACKAGE)
        //получаем фактический результат
        val resultText = device.findObject(By.res(packageName, "text")).text
        assertEquals(resultText, textToSet)
    }


}



