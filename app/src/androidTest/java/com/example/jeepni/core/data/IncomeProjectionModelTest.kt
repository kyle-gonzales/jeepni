import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.jeepni.core.data.IncomeProjectionModel
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.core.app.ApplicationProvider


@RunWith(AndroidJUnit4::class)
class IncomeProjectionModelTest {

    private lateinit var incomeProjectionModel: IncomeProjectionModel

    @Before
    fun setUp() {
        val mockContext = ApplicationProvider.getApplicationContext<Context>()
        incomeProjectionModel = IncomeProjectionModel(mockContext)
    }

    @Test
    fun testTryOpen(){
        incomeProjectionModel.try_open()
    }

    @Test
    fun testModelLoading() {
        // Call loadModel()
        // Call loadModel()
        // Call loadModel()
        incomeProjectionModel.loadModel()

        // Assert that the tfliteModel is not null
        assertNotNull(incomeProjectionModel.tfliteModel)
    }

    @Test
    fun testProjectIncome() {
        val income = 1000.0
        incomeProjectionModel.loadModel() // Assuming model is loaded before inference

        val projectedIncome = incomeProjectionModel.projectIncome(income)

        // Assert that the projected income is a valid value
        // (consider appropriate assertions based on your model's output)
        assertTrue(projectedIncome > 0.0)
    }
}
