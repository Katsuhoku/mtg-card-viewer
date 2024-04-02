package com.example.coroutinesfirebase

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Card(
    val cost: String = "",
    val name: String = "",
    val type: String = "",
    val subtype: String = "",
    val description: String = "",
    val flavor: String = "",
    val color: String = "",
    val power: Int = 0,
    val res: Int = 0
) {
    fun format(): Card {
        return Card(
            cost, name, type, subtype, description.replace("\\n", "\n"), flavor, color, power, res
        )
    }
}

object CardColors {
    val title = mapOf(
        "R" to Color(0xFFDB8E7B),
        "G" to Color(0xFF7fbBA0),
        "U" to Color(0xFF9DC3E3),
        "B" to Color(0xFF6C717A),
        "W" to Color(0xFFD5D6C1),
        "C" to Color(0xFFB3BFC7),
        "G" to Color(0xFFC2B791),
    )

    val description = mapOf(
        "R" to Color(0xFFF5A08C),
        "G" to Color(0xFFA7DBB6),
        "U" to Color(0xFFAED4F5),
        "B" to Color(0xFFB5B2BF),
        "W" to Color(0xFFF4F5E6),
        "C" to Color(0xFFDFECF5),
    )

    val border = mapOf(
        "R" to Color(0xFFC44535),
        "G" to Color(0xFF087334),
        "U" to Color(0xFF245496),
        "B" to Color(0xFF282A2E),
        "W" to Color(0xFFDEDBC8),
        "C" to Color(0xFFD1DCE6),
    )
}


@Composable
fun Card(
    card: Card,
    modifier: Modifier = Modifier,
) {
    val cardColors = if (card.color.length == 1) "${card.color}+${card.color}" else card.color
    val splitColors = cardColors.split("+")

    val cardColorModifier = Modifier
        .clip(RoundedCornerShape(10.dp))
        .background(
            brush = Brush.horizontalGradient(
                colorStops = arrayOf(
                    0.0f to CardColors.title[splitColors[0]]!!,
                    0.45f to CardColors.title[splitColors[0]]!!,
                    0.55f to CardColors.title[splitColors[1]]!!,
                    1f to CardColors.title[splitColors[1]]!!
                )
            )
        )
        .border(
            width = 4.dp,
            brush = Brush.horizontalGradient(
                colorStops = arrayOf(
                    0.0f to CardColors.border[splitColors[0]]!!,
                    0.45f to CardColors.border[splitColors[0]]!!,
                    0.55f to CardColors.border[splitColors[1]]!!,
                    1f to CardColors.border[splitColors[1]]!!
                )
            ),
            shape = RoundedCornerShape(10.dp)
        )

    Box(
        modifier = Modifier
            .size(800.dp, 550.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color(0xFF101010))
            .padding(20.dp)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Title + Cost
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
                    .then(cardColorModifier)
                    .padding(15.dp, 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    letterSpacing = 0.2.sp,
                )

                // Mana cost
                Row(
                    modifier = Modifier
                        .padding(6.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    val manaCost = card.cost.split("+")
                    for (mana in manaCost) {
                        val manaId = when (mana) {
                            "X" -> R.drawable.mana_x
                            "1" -> R.drawable.mana_1
                            "2" -> R.drawable.mana_2
                            "3" -> R.drawable.mana_3
                            "4" -> R.drawable.mana_4
                            "5" -> R.drawable.mana_5
                            "6" -> R.drawable.mana_6
                            "7" -> R.drawable.mana_7
                            "8" -> R.drawable.mana_8
                            "9" -> R.drawable.mana_9
                            "10" -> R.drawable.mana_10
                            "B" -> R.drawable.mana_black
                            "G" -> R.drawable.mana_green
                            "R" -> R.drawable.mana_red
                            "U" -> R.drawable.mana_blue
                            "W" -> R.drawable.mana_white
                            else -> R.drawable.mana_colorless
                        }

                        Box(
                            modifier = Modifier
                                .padding(0.8.dp, 0.dp)
                        ) {
                            Image(
                                painter = painterResource(id = manaId),
                                contentDescription = null
                            )
                        }
                    }
                }
            }

            // Card Art
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(5f)
                    .then(cardColorModifier),
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.art_archivedragon),
                    contentDescription = null
                )
            }

            // Type + Subtype
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
                    .then(cardColorModifier)
                    .padding(15.dp, 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.type,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )

                Text(
                    text = card.subtype,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            }

            // Description
            Column(
               modifier = Modifier
                   .fillMaxWidth()
                   .weight(4f)
                   .then(cardColorModifier)
                   .background(
                       brush = Brush.horizontalGradient(
                           colorStops = arrayOf(
                               0.0f to CardColors.description[splitColors[0]]!!,
                               0.45f to CardColors.description[splitColors[0]]!!,
                               0.55f to CardColors.description[splitColors[1]]!!,
                               1f to CardColors.description[splitColors[1]]!!
                           )
                       )
                   )
                   .padding(15.dp),
            ) {
                val splitDescription = card.description.split("*")
                val annotatedDescription = buildAnnotatedString {
                    for (descriptionPart in splitDescription) {
                        if (descriptionPart.startsWith(">")) {
                            val manaCost = descriptionPart.drop(1).split("+")
                            for (mana in manaCost) appendInlineContent(mana)
                        }
                        else append(descriptionPart)
                    }
                }

                // ****** NEEDS TO BE OPTIMIZED ******* //
                val inlineContentMap = mapOf(
                    "X" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_x),
                            contentDescription = null
                        )
                    },
                    "1" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_1),
                            contentDescription = null
                        )
                    },
                    "2" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_2),
                            contentDescription = null
                        )
                    },
                    "3" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_3),
                            contentDescription = null
                        )
                    },
                    "4" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_4),
                            contentDescription = null
                        )
                    },
                    "5" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_5),
                            contentDescription = null
                        )
                    },
                    "6" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_6),
                            contentDescription = null
                        )
                    },
                    "7" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_7),
                            contentDescription = null
                        )
                    },
                    "8" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_8),
                            contentDescription = null
                        )
                    },
                    "9" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_9),
                            contentDescription = null
                        )
                    },
                    "10" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_10),
                            contentDescription = null
                        )
                    },
                    "R" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_red),
                            contentDescription = null
                        )
                    },
                    "G" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_green),
                            contentDescription = null
                        )
                    },
                    "U" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_blue),
                            contentDescription = null
                        )
                    },
                    "B" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_black),
                            contentDescription = null
                        )
                    },
                    "W" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_white),
                            contentDescription = null
                        )
                    },
                    "C" to InlineTextContent(
                        Placeholder(12.sp, 12.sp, PlaceholderVerticalAlign.TextCenter)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.mana_colorless),
                            contentDescription = null
                        )
                    },
                )

                Text(
                    text = annotatedDescription,
                    inlineContent = inlineContentMap,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Serif,
                    lineHeight = 11.sp
                )

                if (card.flavor.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .padding(0.dp, 8.dp)
                    ) {
                        Divider()

                        Text(
                            text = card.flavor,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Serif,
                            fontStyle = FontStyle.Italic,
                            lineHeight = 10.sp
                        )
                    }

                }
            }
        }

        // Power/Res
        if (card.type.uppercase().contains("CRIATURA")) {
            Box(
                modifier = Modifier
                    .size(60.dp, 30.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colorStops = arrayOf(
                                0.0f to CardColors.title[splitColors[0]]!!,
                                0.45f to CardColors.title[splitColors[0]]!!,
                                0.55f to CardColors.title[splitColors[1]]!!,
                                1f to CardColors.title[splitColors[1]]!!
                            )
                        )
                    )
                    .shadow(20.dp)
                    .align(Alignment.BottomEnd)
                    .padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${card.power}/${card.res}",
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}