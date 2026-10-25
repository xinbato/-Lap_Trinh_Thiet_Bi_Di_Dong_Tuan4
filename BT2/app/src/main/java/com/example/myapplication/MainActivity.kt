package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.CardGray
import com.example.myapplication.ui.theme.LightGrayBg
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.PrimaryBlue
import com.example.myapplication.ui.theme.SecondaryBlueContainer
import com.example.myapplication.ui.theme.StatusGreen
import com.example.myapplication.ui.theme.StatusRed
import com.example.myapplication.ui.theme.TextDark

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                LibraryManagementScreen()
            }
        }
    }
}


interface Borrowable {
    fun borrowItem(): Boolean
    fun returnItem(): Boolean
}

abstract class LibraryItem(
    val id: String,
    title: String
) : Borrowable {
    private var _title: String = title
    val title: String get() = _title

    var isBorrowed: Boolean = false
        protected set

    abstract fun getDetails(): String
}

class Book(
    id: String,
    title: String
) : LibraryItem(id, title) {

    override fun borrowItem(): Boolean {
        return if (!isBorrowed) {
            isBorrowed = true
            true
        } else false
    }

    override fun returnItem(): Boolean {
        return if (isBorrowed) {
            isBorrowed = false
            true
        } else false
    }

    override fun getDetails(): String {
        val status = if (isBorrowed) "Đã mượn" else "Có sẵn"
        return "Sách: $title [$status]"
    }
}

abstract class Person(name: String) {
    private var _name: String = name

    var name: String
        get() = _name
        set(value) {
            if (value.isNotBlank()) {
                _name = value
            }
        }

    abstract fun getRole(): String
}

class Student(
    val id: String,
    name: String,
    val studentId: String
) : Person(name) {

    private val _borrowedBooks = mutableStateListOf<Book>()
    val borrowedBooks: List<Book> get() = _borrowedBooks

    fun borrowBook(book: Book): Boolean {
        if (!_borrowedBooks.contains(book)) {
            _borrowedBooks.add(book)
            book.borrowItem()
            return true
        }
        return false
    }

    fun returnBook(book: Book): Boolean {
        if (_borrowedBooks.contains(book)) {
            _borrowedBooks.remove(book)
            book.returnItem()
            return true
        }
        return false
    }

    override fun getRole(): String {
        return "Sinh viên (MSSV: $studentId)"
    }
}

@Composable
fun LibraryManagementScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    val studentList = remember {
        mutableStateListOf(
            Student("1", "Nguyen Van A", "SV001")
        )
    }

    val bookList = remember {
        mutableStateListOf(
            Book("1", "Sách 01"),
            Book("2", "Sách 02"),
            Book("3", "Sách 03")
        )
    }

    var inputStudentName by remember { mutableStateOf("") }
    var activeStudent by remember { mutableStateOf<Student?>(null) }

    var updateTrigger by remember { mutableIntStateOf(0) }

    var selectedTab by remember { mutableIntStateOf(0) }
    var showAddBookDialog by remember { mutableStateOf(false) }
    var newBookTitle by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.tab_management)) },
                    label = { Text(stringResource(R.string.tab_management), fontSize = 12.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryBlue,
                        selectedTextColor = PrimaryBlue,
                        indicatorColor = SecondaryBlueContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Icon(Icons.Default.Book, contentDescription = stringResource(R.string.tab_book_list)) },
                    label = { Text(stringResource(R.string.tab_book_list), fontSize = 12.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryBlue,
                        selectedTextColor = PrimaryBlue,
                        indicatorColor = SecondaryBlueContainer
                    )
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Icon(Icons.Default.Person, contentDescription = stringResource(R.string.tab_student)) },
                    label = { Text(stringResource(R.string.tab_student), fontSize = 12.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = PrimaryBlue,
                        selectedTextColor = PrimaryBlue,
                        indicatorColor = SecondaryBlueContainer
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.library_management_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            when (selectedTab) {
                0 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Text(
                            text = stringResource(R.string.student_section_title),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedTextField(
                                value = inputStudentName,
                                onValueChange = { inputStudentName = it },
                                placeholder = { Text(stringResource(R.string.student_name_placeholder)) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                singleLine = true
                            )

                            Button(
                                onClick = {
                                    val name = inputStudentName.trim()
                                    if (name.isNotBlank()) {
                                        val existing = studentList.find { it.name.equals(name, ignoreCase = true) }
                                        if (existing != null) {
                                            activeStudent = existing
                                        } else {
                                            val newId = (studentList.size + 1).toString()
                                            val newStudent = Student(newId, name, "SV00$newId")
                                            studentList.add(newStudent)
                                            activeStudent = newStudent
                                        }
                                    } else {
                                        activeStudent = null
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.height(50.dp)
                            ) {
                                Text(stringResource(R.string.btn_change), color = Color.White, fontSize = 14.sp)
                            }
                        }

                        if (activeStudent != null) {
                            Text(
                                text = "Đang chọn: ${activeStudent?.name} (${activeStudent?.studentId})",
                                fontSize = 13.sp,
                                color = StatusGreen,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        } else if (inputStudentName.isNotBlank()) {
                            Text(
                                text = stringResource(R.string.hint_click_change_to_save),
                                fontSize = 13.sp,
                                color = StatusRed,
                                modifier = Modifier.padding(top = 6.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = stringResource(R.string.book_list_section_title),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = CardGray),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                bookList.forEach { book ->
                                    updateTrigger.let {}
                                    val isChecked = activeStudent?.borrowedBooks?.contains(book) == true

                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = Color.White),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Checkbox(
                                                checked = isChecked,
                                                onCheckedChange = { checked ->
                                                    activeStudent?.let { student ->
                                                        if (checked) {
                                                            student.borrowBook(book)
                                                        } else {
                                                            student.returnBook(book)
                                                        }
                                                        updateTrigger++
                                                    }
                                                },
                                                enabled = activeStudent != null,
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = StatusRed,
                                                    uncheckedColor = Color.Gray
                                                )
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = book.title,
                                                fontSize = 15.sp,
                                                fontWeight = FontWeight.Medium,
                                                color = if (activeStudent != null) TextDark else Color.Gray
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Button(
                            onClick = { showAddBookDialog = true },
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.55f)
                                .height(48.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(stringResource(R.string.btn_add), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                1 -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = stringResource(R.string.book_list_title),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            items(bookList) { book ->
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = LightGrayBg),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(text = book.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                        Text(
                                            text = book.getDetails(),
                                            fontSize = 13.sp,
                                            color = if (book.isBorrowed) StatusRed else StatusGreen,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                2 -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = stringResource(R.string.student_list_title),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        if (studentList.isEmpty()) {
                            Text(stringResource(R.string.empty_student_list), fontSize = 14.sp, color = Color.Gray)
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                items(studentList) { studentItem ->
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(containerColor = SecondaryBlueContainer),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Column(modifier = Modifier.padding(16.dp)) {
                                            Text(
                                                text = "Họ tên: ${studentItem.name}",
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )
                                            Text(
                                                text = "Vai trò: ${studentItem.getRole()}",
                                                fontSize = 14.sp,
                                                color = Color.Gray
                                            )
                                            Spacer(modifier = Modifier.height(6.dp))
                                            Text(
                                                text = "Sách đang mượn (${studentItem.borrowedBooks.size}):",
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 14.sp,
                                                color = PrimaryBlue
                                            )

                                            if (studentItem.borrowedBooks.isEmpty()) {
                                                Text(
                                                    text = stringResource(R.string.no_borrowed_books),
                                                    fontSize = 13.sp,
                                                    color = Color.Gray,
                                                    modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                                                )
                                            } else {
                                                studentItem.borrowedBooks.forEach { borrowedBook ->
                                                    Text(
                                                        text = "• ${borrowedBook.title}",
                                                        fontSize = 13.sp,
                                                        color = TextDark,
                                                        modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddBookDialog) {
        AlertDialog(
            onDismissRequest = { showAddBookDialog = false },
            title = { Text(stringResource(R.string.add_book_dialog_title)) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = newBookTitle,
                        onValueChange = { newBookTitle = it },
                        label = { Text(stringResource(R.string.book_title_label)) },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newBookTitle.isNotBlank()) {
                            val newId = (bookList.size + 1).toString()
                            val newBook = Book(newId, newBookTitle)
                            bookList.add(newBook)
                            newBookTitle = ""
                            showAddBookDialog = false
                        }
                    }
                ) {
                    Text(stringResource(R.string.btn_add))
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddBookDialog = false }) {
                    Text(stringResource(R.string.btn_cancel))
                }
            }
        )
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
private fun LibraryManagementScreenLightPreview() {
    MyApplicationTheme(darkTheme = false) {
        LibraryManagementScreen()
    }
}

@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LibraryManagementScreenDarkPreview() {
    MyApplicationTheme(darkTheme = true) {
        LibraryManagementScreen()
    }
}
