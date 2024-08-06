package com.example.myshoppinglistapp.ui.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class ShoppingItems(var quantity:Int,
                         var name:String,
                         var id:Int,
                         var isEditing:Boolean= false
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListAndroid() {
    var sItems by remember { mutableStateOf(listOf<ShoppingItems>()) }
    var showDialog by remember{ mutableStateOf(false) }
    var itemName by remember{ mutableStateOf("")}
    var itemQuantity by remember{ mutableStateOf("")}
    Column(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        Button(
            onClick = {showDialog = true},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    vertical = 50.dp
                )
        ) {
            Text("Add Item")

        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(sItems) {
                item->
                if(item.isEditing)
                {
                    ShoppingListEditor(item = item,
                        onEditComplete = { EditedName,
                                           EditedQuntity ->
                            sItems = sItems.map { it.copy(isEditing = false) }
                            val editedItem = sItems.find { it.id == item.id }
                            editedItem?.let {
                                it.name =EditedName
                                it.quantity = EditedQuntity

                            }
                        }
                    )
                }
                else
                {
                    ShoppingListItem(item = item,
                        onclickEditing = {sItems =sItems.map { it.copy(isEditing = it.id == item.id)}},
                        onClickDelete = {sItems - item}
                        )


            }

            }
        }
    }
    if(showDialog)
    {
        AlertDialog(onDismissRequest = { showDialog=false},
            confirmButton = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween

                            )
                            {
                                Button(onClick = {
                                    if(itemName.isNotBlank())
                                    {
                                        val newItem =ShoppingItems(
                                            name = itemName,
                                            quantity = itemQuantity.toInt(),
                                            id = sItems.size+1
                                        )
                                        showDialog = false
                                        sItems = sItems + newItem
                                        itemName = ""

                                    }
                                })
                                {
                                  Text(text = "Add")
                                }
                                Button(
                                    onClick = { showDialog=false} )
                                {
                                    Text(text = "Cancel")

                                }

                            }
            },
            title = {Text("Add Item")},
            text = {
                Column {
                    OutlinedTextField(value = itemName,
                        onValueChange = {itemName = it},
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                    OutlinedTextField(value = itemQuantity,
                        onValueChange = {itemQuantity = it},
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )


                }

            }

        )
    }
}

@Composable
fun ShoppingListEditor(item: ShoppingItems, onEditComplete:(String,Int)-> Unit)
{
    var EditedName by remember { mutableStateOf(item.name) }
    var EditedQuantity by remember { mutableStateOf(item.quantity.toString()) }
    var isEditing by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    )
    {
        Column {
            BasicTextField(value = EditedName, onValueChange ={EditedName =it },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp),
                singleLine = true)
            BasicTextField(value = EditedQuantity, onValueChange ={EditedQuantity =it },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp),
                singleLine = true)
        }
        Button(
            onClick = {
                isEditing =false
                onEditComplete(EditedName, EditedQuantity.toIntOrNull() ?: 1)
            }
        )
        {
            Text(text = "Save")
        }

    }
}

@Composable
fun ShoppingListItem(
    item: ShoppingItems,
    onclickEditing: ()->Unit,
    onClickDelete: ()->Unit
)
{
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(2.dp, Color(4294954137)),
                shape = RoundedCornerShape(20)
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.name, modifier = Modifier.padding(8.dp))
        Text(text = "Qty:${item.quantity}", modifier = Modifier.padding(8.dp))

        Row(modifier = Modifier.padding(8.dp))
        {
            IconButton(
                onClick =onclickEditing ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription =null )

            }
            IconButton(
                onClick =onClickDelete ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription =null )

            }

        }

    }

}

