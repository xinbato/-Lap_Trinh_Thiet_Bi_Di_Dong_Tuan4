package com.example.uthtodo.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uthtodo.model.OnboardingCardType
import com.example.uthtodo.model.OnboardingPageModel
import com.example.uthtodo.ui.theme.BorderGray
import com.example.uthtodo.ui.theme.Dimens
import com.example.uthtodo.ui.theme.IndicatorInactive
import com.example.uthtodo.ui.theme.OrangeTagBg
import com.example.uthtodo.ui.theme.OrangeTagText
import com.example.uthtodo.ui.theme.PrimaryBlue
import com.example.uthtodo.ui.theme.PureWhite
import com.example.uthtodo.ui.theme.SoftBlueBg
import com.example.uthtodo.ui.theme.TextDark
import com.example.uthtodo.ui.theme.TextSecondary

@Composable
fun OnboardingScreen(
    pageModel: OnboardingPageModel,
    onNextClick: () -> Unit,
    onSkipClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PureWhite)
            .padding(Dimens.PaddingLarge),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.PaddingMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(36.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = PrimaryBlue
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CheckCircleIcon(
                                    modifier = Modifier.size(12.dp),
                                    circleColor = PureWhite,
                                    checkColor = PrimaryBlue
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Box(
                                    modifier = Modifier
                                        .height(4.dp)
                                        .width(14.dp)
                                        .clip(CircleShape)
                                        .background(PureWhite)
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(SoftBlueBg)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Box(
                                    modifier = Modifier
                                        .height(4.dp)
                                        .width(10.dp)
                                        .clip(CircleShape)
                                        .background(SoftBlueBg)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = TextDark)) {
                            append("UTH ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, color = PrimaryBlue)) {
                            append("Todo")
                        }
                    },
                    fontSize = 20.sp
                )
            }

            Text(
                text = "Bỏ qua",
                color = TextSecondary,
                fontSize = Dimens.FontBodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable { onSkipClick() }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(SoftBlueBg)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            when (pageModel.cardType) {
                OnboardingCardType.TASK_LIST -> OnboardingCard1a()
                OnboardingCardType.CATEGORIES -> OnboardingCard1b()
                OnboardingCardType.NOTIFICATION -> OnboardingCard1c()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = pageModel.title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                lineHeight = 32.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = pageModel.description,
                fontSize = Dimens.FontBodyMedium,
                color = TextSecondary,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(3) { index ->
                    if (index == pageModel.stepIndex) {
                        Box(
                            modifier = Modifier
                                .width(28.dp)
                                .height(8.dp)
                                .clip(CircleShape)
                                .background(PrimaryBlue)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(IndicatorInactive)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.ButtonHeight),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
            ) {
                Text(
                    text = pageModel.buttonText,
                    color = PureWhite,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun OnboardingCard1a() {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = PureWhite
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CheckCircleIcon(modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(110.dp)
                        .clip(CircleShape)
                        .background(BorderGray)
                )
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = PureWhite
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .border(2.dp, IndicatorInactive, CircleShape)
                )
                Spacer(modifier = Modifier.width(14.dp))
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(90.dp)
                        .clip(CircleShape)
                        .background(BorderGray)
                )
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            color = PureWhite
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .border(2.dp, IndicatorInactive, CircleShape)
                )
                Spacer(modifier = Modifier.width(14.dp))
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(130.dp)
                        .clip(CircleShape)
                        .background(BorderGray)
                )
            }
        }
    }
}

@Composable
private fun OnboardingCard1b() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Tag Card 1: Học tập
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = PureWhite
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SoftBlueBg)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Học tập",
                        color = PrimaryBlue,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(140.dp)
                        .clip(CircleShape)
                        .background(BorderGray)
                )
            }
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            color = PureWhite
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(OrangeTagBg)
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Cuộc sống",
                        color = OrangeTagText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .height(8.dp)
                        .width(110.dp)
                        .clip(CircleShape)
                        .background(BorderGray)
                )
            }
        }
    }
}

@Composable
private fun OnboardingCard1c() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            modifier = Modifier.size(76.dp),
            shape = CircleShape,
            color = PureWhite,
            shadowElevation = 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                BellIcon(modifier = Modifier.size(38.dp))
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = PureWhite,
            shadowElevation = 2.dp
        ) {
            Text(
                text = "Ôn bài Toán lúc 19:00",
                color = TextDark,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp)
            )
        }
    }
}

@Composable
fun BellIcon(
    modifier: Modifier = Modifier,
    color: Color = PrimaryBlue
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val strokeWidth = w * 0.08f
        val path = Path().apply {
            moveTo(w * 0.5f, h * 0.15f)
            cubicTo(w * 0.3f, h * 0.15f, w * 0.25f, h * 0.35f, w * 0.25f, h * 0.55f)
            lineTo(w * 0.12f, h * 0.72f)
            lineTo(w * 0.88f, h * 0.72f)
            lineTo(w * 0.75f, h * 0.55f)
            cubicTo(w * 0.75f, h * 0.35f, w * 0.7f, h * 0.15f, w * 0.5f, h * 0.15f)
            close()
        }
        drawPath(path = path, color = color, style = Stroke(width = strokeWidth, cap = StrokeCap.Round))
        drawCircle(color = color, radius = w * 0.07f, center = Offset(w * 0.5f, h * 0.82f))
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreen1aPreview() {
    OnboardingScreen(
        pageModel = OnboardingPageModel.getPages()[0],
        onNextClick = {},
        onSkipClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreen1bPreview() {
    OnboardingScreen(
        pageModel = OnboardingPageModel.getPages()[1],
        onNextClick = {},
        onSkipClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreen1cPreview() {
    OnboardingScreen(
        pageModel = OnboardingPageModel.getPages()[2],
        onNextClick = {},
        onSkipClick = {}
    )
}
