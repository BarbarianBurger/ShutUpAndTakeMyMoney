package demo.lina.shutupandtakemymoney

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import demo.lina.shutupandtakemymoney.data.Record
import demo.lina.shutupandtakemymoney.ui.theme.ShutUpAndTakeMyMoneyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShutUpAndTakeMyMoneyTheme {
                MainPage(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MainPage(modifier: Modifier = Modifier) {
    // A surface container using the 'background' color from the theme
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // rememberSaveable: can save the state while activity restart
        // var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
        val recordList = remember { mutableStateListOf<Record>()}
        Column() {
            addItem(recordList)
            ShowRecordList(recordList)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun addItem(recordList: MutableList<Record>){
    Row(
        Modifier
            .padding(16.dp, 0.dp)
            .height(84.dp), verticalAlignment = Alignment.CenterVertically) {
        var itemName by remember { mutableStateOf("") }
        var itemPrice by remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current
        OutlinedTextField(
            value = itemName,
            onValueChange = { newValue ->
                itemName = newValue
            },
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
            label = { Text("項目名稱") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        )

        OutlinedTextField(
            value = itemPrice,
            onValueChange = { newValue ->
                itemPrice = newValue
            },
            modifier = Modifier
                .padding(5.dp)
                .weight(1f),
            label = { Text("價格") },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        )

        Button(shape= RoundedCornerShape(10.dp),onClick = {
                if (itemName.isNotEmpty() && itemPrice.isNotEmpty()) {
                    recordList.add(Record(itemName, itemPrice.toInt(), "日用品"))
                    itemName = "" // 清空輸入的項目名稱
                    itemPrice = "" // 清空輸入的價格
                    recordList.forEach { record ->
                        Log.d("RecordList", record.toString())
                }
            }

        }, modifier = Modifier
            .padding(15.dp, 0.dp, 0.dp,0.dp)
            .size(50.dp)
            .weight(1f)

        ) {
            Text("Add")
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun RowScope.TextFieldItem(hint: String) {
//    var searchText by remember { mutableStateOf("") }
//    TextField(
//        value = searchText,
//        onValueChange = { newText ->
//            searchText = newText
//        },
//        Modifier
//            .padding(5.dp)
//            .size(40.dp)
//            .weight(1f)
//    )
//}
@Composable
private fun ShowRecordList(recordList: MutableList<Record>,
                           modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = recordList) { record ->
            ShowRecord(record = record)
        }
    }
}

@Composable
private fun ShowRecord(record: Record, modifier: Modifier = Modifier) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = record.name,
            fontSize = 24.sp,
            modifier = Modifier.padding(10.dp)
        )

        Text(
            text = record.amount.toString(),
            fontSize = 24.sp,
            modifier = Modifier.padding(10 .dp)
        )
    }
}


//@Preview(showBackground = false)
//@Composable
//fun DefaultPreview() {
//    ShutUpAndTakeMyMoneyTheme {
//        val recordList = mutableListOf(
//            Record("早餐", 50, "食物"),
//            Record("便當", 100, "食物"),
//            Record("晚餐", 65, "食物"),
//            Record("餅乾", 135, "食物"),
//            Record("手搖", 55, "食物"),
//            Record("洗衣網", 198, "日用品"),
//            Record("棉條", 505, "日用品"),
//            Record("排球場地費", 150, "運動"),
//            Record("溜冰", 350, "娛樂"),
//        )
//        ShowRecordList(recordList)
//    }
//}