package com.example.uthtodo.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthtodo.model.StartPageModel
import com.example.uthtodo.ui.theme.Dimens
import com.example.uthtodo.ui.theme.PrimaryBlue
import com.example.uthtodo.ui.theme.PureWhite
import com.example.uthtodo.ui.theme.SoftBlueBg
import com.example.uthtodo.ui.theme.UTHTodoTheme
import com.example.uthtodo.ui.theme.WhiteAlpha28
import com.example.uthtodo.ui.theme.WhiteAlpha90

@Composable
fun StartScreen(
    data: StartPageModel = StartPageModel(),
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryBlue)
            .clickable { onStartClick() }
            .padding(Dimens.PaddingLarge),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier.size(110.dp),
                shape = RoundedCornerShape(28.dp),
                color = PureWhite,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CheckCircleIcon(modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .width(40.dp)
                                .clip(CircleShape)
                                .background(PrimaryBlue)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .background(SoftBlueBg),
                            contentAlignment = Alignment.Center
                        ){
                            Box(
                                modifier = Modifier
                                    .size(14.dp)
                                    .clip(CircleShape)
                                    .background(PureWhite)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .width(30.dp)
                                .clip(CircleShape)
                                .background(SoftBlueBg)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(data.titleBold)
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(data.titleRegular)
                    }
                },
                color = PureWhite,
                fontSize = 36.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = data.subtitle,
                color = WhiteAlpha90,
                fontSize = Dimens.FontBodyMedium,
                fontWeight = FontWeight.Normal
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Dimens.PaddingLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .width(110.dp)
                    .height(4.dp)
                    .clip(CircleShape)
                    .background(WhiteAlpha28)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(4.dp)
                        .clip(CircleShape)
                        .background(PureWhite)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = data.version,
                color = WhiteAlpha90,
                fontSize = Dimens.FontCaption
            )
        }
    }
}

@Composable
fun CheckCircleIcon(
    modifier: Modifier = Modifier,
    circleColor: Color = PrimaryBlue,
    checkColor: Color = PureWhite
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(circleColor),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize(0.6f)) {
            val path = Path().apply {
                moveTo(size.width * 0.15f, size.height * 0.5f)
                lineTo(size.width * 0.42f, size.height * 0.78f)
                lineTo(size.width * 0.88f, size.height * 0.22f)
            }
            drawPath(
                path = path,
                color = checkColor,
                style = Stroke(width = size.width * 0.22f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    UTHTodoTheme {
        StartScreen(onStartClick = {})
    }
}
