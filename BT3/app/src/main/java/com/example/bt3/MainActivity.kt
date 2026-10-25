@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.bt3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bt3.ui.theme.BT3Theme

val GreenPrimary = Color(0xFF000000)
val BorderLight = Color(0xFFE5E7EB)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BT3Theme {
               ResetPasswordApp()
            }
        }
    }
}

class ResetPasswordViewModel : ViewModel() {
    var email by mutableStateOf("")
    var otp by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    var submittedEmail by mutableStateOf("")
    var submittedOtp by mutableStateOf("")
    var submittedPassword by mutableStateOf("")

    fun submitData() {
        submittedPassword = newPassword
        submittedOtp = otp
        submittedEmail = email
    }

    fun resetAll() {
        email = ""
        otp = ""
        newPassword = ""
        confirmPassword = ""
    }
}

@Composable
fun ForgetPasswordScreen(
    viewModel: ResetPasswordViewModel,
    onNext: () -> Unit
) {
    val image = painterResource(R.drawable.img)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
            )
        },
    ){paddingValues ->
        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
         ) {
        Image(
            painter = image,
            contentDescription = null,
            modifier = Modifier.size(150.dp)

        )
        Text(text = "SmartTasks", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF688CEA))
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Forget Password?", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(text = "Enter your Email, we will send you a verification code.", fontSize = 12.sp, color = Color.Gray)

        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "email") },
            placeholder = { Text("Your Email") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF688CEA)),
                ) {
                Text("Next")
            }
        Spacer(modifier = Modifier.height(20.dp))
        if (viewModel.submittedEmail.isNotEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFC2E0E5)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = viewModel.submittedEmail,
                        color = Color(0xFF000000),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = viewModel.submittedOtp,
                        color = Color(0xFF000000),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = viewModel.submittedPassword,
                        color = Color(0xFF000000),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        }
    }
}

@Composable
fun VerificationScreen(
    viewModel: ResetPasswordViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val image = painterResource(R.drawable.img)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back",
                            )
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.size(150.dp)

            )
            Text(text = "SmartTasks", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF688CEA))
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Verify Code", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "Enter the code we just sent you on your registered Email", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                BasicTextField(
                    value = viewModel.otp,
                    onValueChange = { newValue ->
                        if (newValue.length <= 5 && newValue.all { it.isDigit() }) {
                            viewModel.otp = newValue
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    decorationBox = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            repeat(5) { index ->
                                val digit = viewModel.otp.getOrNull(index)?.toString() ?: ""
                                val isFocusedBox = viewModel.otp.length == index || (viewModel.otp.length == 5 && index == 4)
                                val borderColor = if (isFocusedBox || digit.isNotEmpty()) Color.Black else Color.Gray

                                Box(
                                    modifier = Modifier
                                        .size(width = 52.dp, height = 58.dp)
                                        .background(Color.White, RoundedCornerShape(14.dp))
                                        .border(1.5.dp, borderColor, RoundedCornerShape(14.dp)),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Text(
                                        text = digit,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                        }
                    },
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onNext,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF688CEA)),
                ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel,
    onNext: () -> Unit,
    onBack: () -> Unit
) {
    val image = painterResource(R.drawable.img)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.size(150.dp)

            )
            Text(text = "SmartTasks", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF688CEA))
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Create new password", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "Your new password must be different form previously used password", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(20.dp))
            val isPasswordMatching = viewModel.newPassword.isNotEmpty() && viewModel.newPassword == viewModel.confirmPassword
            val showMismatchError = viewModel.confirmPassword.isNotEmpty() && !isPasswordMatching
            var passwordVisible by remember { mutableStateOf(false) }
            var confirmPasswordVisible by remember { mutableStateOf(false) }

            OutlinedTextField(
                value = viewModel.newPassword,
                onValueChange = { viewModel.newPassword = it },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
                placeholder = { Text(text = "Password") },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    val description = if (passwordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = icon, contentDescription = description)
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = viewModel.confirmPassword,
                onValueChange = { viewModel.confirmPassword = it },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Confirm Password") },
                placeholder = { Text(text = "Confirm Password") },
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    val description = if (confirmPasswordVisible) "Ẩn mật khẩu" else "Hiện mật khẩu"
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(imageVector = icon, contentDescription = description)
                    }
                },
                isError = showMismatchError,
                supportingText = if (showMismatchError) {
                    { Text("Mật khẩu không khớp", color = Color.Red) }
                } else null,
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onNext,
                enabled = isPasswordMatching,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF688CEA)),
                ) {
                Text("Next")
            }
        }
    }
}

@Composable
fun ConfirmScreen(
    viewModel: ResetPasswordViewModel,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    val image = painterResource(R.drawable.img)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.size(150.dp)

            )
            Text(text = "SmartTasks", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF688CEA))
            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Confirm", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(text = "We are here to help you!", fontSize = 12.sp, color = Color.Gray)
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = {},
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Email") },
                enabled = false,
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                value = viewModel.otp,
                onValueChange = {},
                leadingIcon = { Icon(Icons.Default.Mail, contentDescription = "OTP") },
                enabled = false,
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                value = viewModel.newPassword,
                onValueChange = {},
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
                enabled = false,
                shape = RoundedCornerShape(12.dp)
            )


            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    viewModel.submitData()
                onSubmit()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF688CEA)),
                ) {
                Text("Submit")
            }
        }
    }
}

@Composable
fun ResetPasswordApp() {
    val navController = rememberNavController()
    val viewModel: ResetPasswordViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "forget_password"
    ) {
        composable("forget_password") {
            ForgetPasswordScreen(
                viewModel = viewModel,
                onNext = { navController.navigate("verification") }
            )
        }

        composable("verification") {
            VerificationScreen(
                viewModel = viewModel,
                onNext = { navController.navigate("reset_password") },
                onBack = { navController.popBackStack() }
            )
        }

        composable("reset_password") {
            ResetPasswordScreen(
                viewModel = viewModel,
                onNext = { navController.navigate("confirm") },
                onBack = { navController.popBackStack() }
            )
        }

        composable("confirm") {
            ConfirmScreen(
                viewModel = viewModel,
                onSubmit = {
                    navController.popBackStack("forget_password", inclusive = false)
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}


