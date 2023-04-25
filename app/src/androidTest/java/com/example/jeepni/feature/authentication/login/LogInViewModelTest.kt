import com.example.jeepni.core.data.repository.TestAuthRepositoryImpl
import com.example.jeepni.core.data.repository.TestUserDetailRepositoryImpl
import com.example.jeepni.feature.authentication.login.LogInEvent
import com.example.jeepni.feature.authentication.login.LogInViewModel
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class LogInViewModelTest {

    @Test
    fun TestInvalidEmail() {
        val BAD_EMAIL = "I'm not a real email"

        val viewModel = LogInViewModel(TestAuthRepositoryImpl(), TestUserDetailRepositoryImpl())

        viewModel.onEvent((LogInEvent.OnEmailChange(BAD_EMAIL)))

        assertTrue(viewModel.email == BAD_EMAIL)
        viewModel.onEvent((LogInEvent.OnLogInClicked))
        // ViewModel detects invalid email after pressing LogIn button
        assertFalse(viewModel.validEmail)
    }
}