package com.jotaro.catai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import kotlin.math.min
import com.jotaro.catai.ui.theme.CatAiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatAiTheme {
                CatAiCompanionApp()
            }
        }
    }
}

enum class CatMood(
    val label: String,
    val response: String,
    val accent: Color
) {
    CURIOUS("Немного любопытный", "Смотри, я уже заметил твой контекст. Дай мне команду, и я быстро дам подсказку.", Color(0xFF83E6FF)),
    ALERT("На чеку", "Я вижу твою активность. Действие зафиксировано, держу ситуацию под контролем.", Color(0xFFFFB067)),
    HAPPY("Рад помочь", "У тебя всё под контролем. Я быстро подготовлю полезную подсказку и поможем без лишнего шума.", Color(0xFF7BE7A3)),
    THINKING("Думает", "Сейчас сверяю детали и выбираю лучший вариант. Будет коротко и с пользой.", Color(0xFFC7A8FF)),
    PLAYFUL("Подмигивает", "Погнали! Я уже готов сделать шаг дальше и предсказать, что тебе нужно.", Color(0xFFFFD86F)),
    SHY("Смущён", "Я немного замедлился, но не ушёл. Давай просто сделаем это шаг за шагом.", Color(0xFFFFA9D9)),
    EXCITED("В восторге", "Да! У тебя отличная идея. Я уже вижу следующий полезный шаг.", Color(0xFF8AE5D0)),
    WILD("Дикий взгляд", "Сейчас я читаю ситуацию с большей внимательностью — и это точно даст лучший результат.", Color(0xFFE58A87));
}

@Composable
fun CatAiCompanionApp() {
    var mood by remember { mutableStateOf(CatMood.CURIOUS) }
    val suggestions = listOf(
        "Объяснить задачу на 3 шага",
        "Проверить текущий экран",
        "Предложить идею решения",
        "Скорректировать приоритеты",
        "Показать короткий план"
    )

    val reactions = listOf(
        CatMood.HAPPY,
        CatMood.ALERT,
        CatMood.THINKING,
        CatMood.PLAYFUL,
        CatMood.EXCITED,
        CatMood.WILD,
        CatMood.SHY,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF090B16),
                        Color(0xFF101A2B),
                        Color(0xFF1D1230)
                    )
                )
            )
            .padding(18.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Cat AI Companion",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(22.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF171D2A).copy(alpha = 0.88f))
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CatFace(mood = mood, modifier = Modifier.size(280.dp))

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = mood.label,
                        style = MaterialTheme.typography.titleLarge,
                        color = mood.accent,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = mood.response,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFFE6ECFF),
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(reactions) { reaction ->
                    val isSelected = reaction == mood
                    Card(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .clickable { mood = reaction },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) reaction.accent.copy(alpha = 0.25f) else Color(0xFF111A2E)
                        )
                    ) {
                        Text(
                            text = reaction.label,
                            color = if (isSelected) reaction.accent else Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { mood = CatMood.ALERT },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F7AE0))
                ) {
                    Text("Проанализировать экран")
                }

                Button(
                    onClick = { mood = CatMood.EXCITED },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6))
                ) {
                    Text("Подсказать мне")
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                suggestions.forEach { suggestion ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF121B2E)),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = suggestion,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(
                                        color = mood.accent,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CatFace(mood: CatMood, modifier: Modifier = Modifier) {
    val headSize = 220f
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cx = size.width / 2f
            val cy = size.height / 2f

            drawRoundRect(
                color = Color(0xFF111111),
                topLeft = Offset(cx - headSize * 0.44f, cy - headSize * 0.38f),
                size = Size(headSize * 0.88f, headSize * 0.76f),
                cornerRadius = CornerRadius(headSize * 0.22f, headSize * 0.22f)
            )

            val earPathLeft = Path().apply {
                moveTo(cx - headSize * 0.20f, cy - headSize * 0.28f)
                lineTo(cx - headSize * 0.22f, cy - headSize * 0.58f)
                lineTo(cx - headSize * 0.06f, cy - headSize * 0.30f)
                close()
            }
            val earPathRight = Path().apply {
                moveTo(cx + headSize * 0.20f, cy - headSize * 0.28f)
                lineTo(cx + headSize * 0.22f, cy - headSize * 0.58f)
                lineTo(cx + headSize * 0.06f, cy - headSize * 0.30f)
                close()
            }
            drawPath(earPathLeft, color = Color(0xFF0C0C0D))
            drawPath(earPathRight, color = Color(0xFF0C0C0D))

            val leftEyeRect = Rect(
                left = cx - headSize * 0.24f,
                top = cy - headSize * 0.16f,
                right = cx - headSize * 0.03f,
                bottom = cy + headSize * 0.12f
            )
            val rightEyeRect = Rect(
                left = cx + headSize * 0.03f,
                top = cy - headSize * 0.16f,
                right = cx + headSize * 0.24f,
                bottom = cy + headSize * 0.12f
            )

            drawRoundRect(
                color = Color(0xFFFFFFFF),
                topLeft = Offset(leftEyeRect.left, leftEyeRect.top),
                size = Size(leftEyeRect.width, leftEyeRect.height),
                cornerRadius = CornerRadius(leftEyeRect.width / 2f, leftEyeRect.height / 2f)
            )
            drawRoundRect(
                color = Color(0xFFFFFFFF),
                topLeft = Offset(rightEyeRect.left, rightEyeRect.top),
                size = Size(rightEyeRect.width, rightEyeRect.height),
                cornerRadius = CornerRadius(rightEyeRect.width / 2f, rightEyeRect.height / 2f)
            )

            val eyeScale = when (mood) {
                CatMood.SHY -> 0.7f
                CatMood.THINKING -> 0.8f
                CatMood.WILD -> 1.0f
                else -> 0.82f
            }

            val pupilLeftSize = headSize * 0.12f * eyeScale
            val pupilRightSize = headSize * 0.12f * eyeScale
            drawRoundRect(
                color = Color(0xFF1A1A1A),
                topLeft = Offset(cx - headSize * 0.17f, cy - headSize * 0.1f),
                size = Size(pupilLeftSize, pupilLeftSize * 0.8f),
                cornerRadius = CornerRadius(18f, 18f)
            )
            drawRoundRect(
                color = Color(0xFF1A1A1A),
                topLeft = Offset(cx + headSize * 0.05f, cy - headSize * 0.1f),
                size = Size(pupilRightSize, pupilRightSize * 0.8f),
                cornerRadius = CornerRadius(18f, 18f)
            )

            drawCircle(color = Color(0xFFFFC7C7), radius = headSize * 0.055f, center = Offset(cx - headSize * 0.15f, cy + headSize * 0.08f))
            drawCircle(color = Color(0xFFFFC7C7), radius = headSize * 0.055f, center = Offset(cx + headSize * 0.15f, cy + headSize * 0.08f))

            drawLine(
                color = Color(0xFF111111),
                start = Offset(cx - headSize * 0.24f, cy + headSize * 0.10f),
                end = Offset(cx - headSize * 0.04f, cy + headSize * 0.18f),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = Color(0xFF111111),
                start = Offset(cx + headSize * 0.24f, cy + headSize * 0.10f),
                end = Offset(cx + headSize * 0.04f, cy + headSize * 0.18f),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )

            drawLine(
                color = Color(0xFF1F2023),
                start = Offset(cx - headSize * 0.28f, cy + headSize * 0.26f),
                end = Offset(cx - headSize * 0.12f, cy + headSize * 0.36f),
                strokeWidth = 2.5f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = Color(0xFF1F2023),
                start = Offset(cx + headSize * 0.28f, cy + headSize * 0.26f),
                end = Offset(cx + headSize * 0.12f, cy + headSize * 0.36f),
                strokeWidth = 2.5f,
                cap = StrokeCap.Round
            )

            val mouthCurve = when (mood) {
                CatMood.HAPPY -> 0.9f
                CatMood.EXCITED -> 1.1f
                CatMood.PLAYFUL -> 1.2f
                CatMood.SHY -> 0.4f
                CatMood.WILD -> 0.7f
                else -> 0.8f
            }

            drawArc(
                color = Color(0xFF2A2F35),
                startAngle = 200f,
                sweepAngle = 120f * mouthCurve,
                useCenter = false,
                topLeft = Offset(cx - headSize * 0.14f, cy + headSize * 0.28f),
                size = Size(headSize * 0.28f, headSize * 0.18f),
                style = Stroke(width = 5f)
            )

            val browColor = if (mood == CatMood.WILD) Color(0xFF101010) else Color(0xFF06070A)
            drawLine(
                color = browColor,
                start = Offset(cx - headSize * 0.26f, cy - headSize * 0.24f),
                end = Offset(cx - headSize * 0.03f, cy - headSize * 0.18f),
                strokeWidth = 4.5f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = browColor,
                start = Offset(cx + headSize * 0.03f, cy - headSize * 0.18f),
                end = Offset(cx + headSize * 0.26f, cy - headSize * 0.24f),
                strokeWidth = 4.5f,
                cap = StrokeCap.Round
            )
        }
    }
}
