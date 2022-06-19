package com.example.firestorecomposedemo.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.firestorecomposedemo.components.MyTextField
import com.example.firestorecomposedemo.components.ShowTotalSavings
import com.example.firestorecomposedemo.model.storedData

@Composable
fun MainScreen(myViewModel: MyViewModel) {

    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ShowTotalSavings(myViewModel.calculatedSum.toString())

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )
            MyTextField(
                label = "Adjust amount",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                // This value is shown in the text field
                value = myViewModel.adjustSavings,
                // This should update the state of value field
                onValueChanged = { myViewModel.onMySavingsChanged(it) })
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = { myViewModel.createFirebaseDocument() }) {
                    Text(text = "add amount")

                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "\n" +
                            "Deductible amount")

                }

            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            Button(onClick = { myViewModel.clearTotal() }) {
                Text(text = "Clear All")

            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )

            // With StateFlow - Here I collect the flow from the viewModel
            var collectedFlow = myViewModel.name.collectAsState()
            MyTextField(
                label = "Name from viewModel",
                // value = myViewModel.name.value,
                value = collectedFlow.value,
                singleLine = true,
                onValueChanged = { newText ->
                    myViewModel.setName(newText) }

            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            )


            // Read the data from firestore collection users - document holidaySavings - Field name
            // From Stackoverflow answer
            var newStringFromStoredData by remember {
                mutableStateOf("")
            }
            Text(newStringFromStoredData)

            LaunchedEffect(newStringFromStoredData) {
                newStringFromStoredData =
                    try { storedData().readDataFromFireStoreFieldName() } catch(e: Exception) { "Error!" }
            }
        }

        // FIRESTORE TESTING
        // Create a firebaseFirestore document - Comment his out, to not have it create a document each time i run
        //myViewModel.CreateFirebaseDocument()

        // Update firestore test
        myViewModel.UpdateFireStoreData("Knud", 850)



    }
}

@Preview
@Composable
fun myPreview() {
    MainScreen(myViewModel = MyViewModel())
}
