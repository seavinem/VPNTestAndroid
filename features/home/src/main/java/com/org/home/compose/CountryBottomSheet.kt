package com.org.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.org.design_system.theme.VpnColors
import com.org.design_system.theme.VpnDemoTheme
import com.org.design_system.theme.VpnTextStyle
import com.org.home.model.CountryDomain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryBottomSheet(
    countries: List<CountryDomain>,
    selectedCountry: CountryDomain?,
    searchQuery: String,
    sheetState: SheetState,
    onSearchQueryChanged: (String) -> Unit,
    onCountrySelected: (CountryDomain) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filtered = remember(countries, searchQuery) {
        if (searchQuery.isBlank()) countries
        else countries.filter {
            it.countryName.contains(searchQuery, ignoreCase = true) ||
                it.capital.contains(searchQuery, ignoreCase = true)
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = modifier,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        containerColor = Color(0xFF121C27),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .background(VpnColors.Outline.copy(alpha = 0.2f), RoundedCornerShape(2.dp))
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text = "Select Location",
                    style = VpnTextStyle.SheetTitle,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                SearchField(
                    query = searchQuery,
                    onQueryChanged = onSearchQueryChanged
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = filtered, key = { it.countryName }) { country ->
                    CountryListItem(
                        country = country,
                        isSelected = country.countryName == selectedCountry?.countryName,
                        onClick = { onCountrySelected(country) }
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchField(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = query,
        onValueChange = onQueryChanged,
        singleLine = true,
        cursorBrush = SolidColor(VpnColors.Primary),
        textStyle = TextStyle(color = VpnColors.OnSurface, fontSize = 15.sp),
        modifier = modifier.fillMaxWidth(),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White.copy(alpha = 0.06f), RoundedCornerShape(16.dp))
                    .border(1.dp, VpnColors.Border, RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = VpnColors.Outline,
                    modifier = Modifier.size(20.dp)
                )
                Box {
                    if (query.isEmpty()) {
                        Text(
                            text = "Search countries or cities...",
                            style = TextStyle(color = VpnColors.Outline, fontSize = 15.sp)
                        )
                    }
                    innerTextField()
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, backgroundColor = 0xFF0F1923)
@Composable
private fun CountryBottomSheetPreview() {
    VpnDemoTheme {
        val countries = listOf(
            CountryDomain(countryName = "Germany", capital = "Berlin", flagUrl = ""),
            CountryDomain(countryName = "United States", capital = "Washington D.C.", flagUrl = ""),
            CountryDomain(countryName = "Japan", capital = "Tokyo", flagUrl = ""),
            CountryDomain(countryName = "Netherlands", capital = "Amsterdam", flagUrl = ""),
        )
        CountryBottomSheet(
            countries = countries,
            selectedCountry = countries.first(),
            searchQuery = "",
            sheetState = rememberModalBottomSheetState(),
            onSearchQueryChanged = {},
            onCountrySelected = {},
            onDismiss = {}
        )
    }
}