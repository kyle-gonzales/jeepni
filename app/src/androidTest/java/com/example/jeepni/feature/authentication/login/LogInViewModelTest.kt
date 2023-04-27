import com.example.jeepni.core.data.repository.TestAuthRepositoryImpl
import com.example.jeepni.core.data.repository.TestUserDetailRepositoryImpl
import com.example.jeepni.feature.authentication.login.LogInEvent
import com.example.jeepni.feature.authentication.login.LogInViewModel
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class LogInViewModelTest {

    private var BAD_EMAIL = "I'm not a real email"
    private var NULL_EMAIL = ""
    private val GOOD_EMAIL = "test@email.com"
    private lateinit var viewModel: LogInViewModel

    @Before
    fun setUp() {
        viewModel = LogInViewModel(TestAuthRepositoryImpl(), TestUserDetailRepositoryImpl())
    }

    @Test
    fun TestInvalidEmail() {
        viewModel.onEvent((LogInEvent.OnEmailChange(BAD_EMAIL)))
        assertTrue(viewModel.email == BAD_EMAIL)
        viewModel.onEvent((LogInEvent.OnLogInClicked))
        // ViewModel detects invalid email after pressing LogIn button
        assertFalse(viewModel.validEmail)
    }

    @Test
    fun TestNullEmail() {
        viewModel.onEvent((LogInEvent.OnEmailChange(NULL_EMAIL)))
        assertTrue(viewModel.email == NULL_EMAIL)
        viewModel.onEvent((LogInEvent.OnLogInClicked))
        // ViewModel detects null email after pressing LogIn button
        assertFalse(viewModel.validEmail)
    }

    @Test
    fun TestWorkingEmail() {
        viewModel.onEvent((LogInEvent.OnEmailChange(GOOD_EMAIL)))
        assertTrue(viewModel.email == GOOD_EMAIL)
        viewModel.onEvent((LogInEvent.OnLogInClicked))
        // ViewModel detects valid email after pressing LogIn button
        assertTrue(viewModel.validEmail)
    }
}