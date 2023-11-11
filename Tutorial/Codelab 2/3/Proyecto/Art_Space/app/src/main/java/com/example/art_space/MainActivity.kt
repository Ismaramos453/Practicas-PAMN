package com.example.art_space

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.art_space.ui.theme.Art_SpaceTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.ButtonDefaults

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Art_SpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtApp()
                }
            }
        }
    }
}
// ArtApp es el composable raíz que se muestra en la pantalla principal.
@Composable
fun ArtApp() {
    var currentIndex by remember { mutableStateOf(0) }
    val arts = remember { values() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF096EBE))
            .padding(16.dp)
    ) {

        // La navegación se mueve dentro de DisplayArtworkInformation
        DisplayInformation(
            artPiece = arts[currentIndex],
            currentIndex = currentIndex,
            artCount = arts.size,
            onIndexChange = { newIndex -> currentIndex = newIndex }
        )
    }
}

// GalleryNavigationRow es el composable que crea la fila de botones de navegación.
@Composable
fun GalleryNavigationRow(currentIndex: Int, artworksCount: Int, onIndexChange: (Int) -> Unit) {
    // Define el color del texto del botón para que contraste con el fondo blanco
    val buttonTextColor = Color.Black

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        if (artworksCount > 1) {
            TextButton(
                onClick = { onIndexChange((currentIndex - 1 + artworksCount) % artworksCount) },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = buttonTextColor, // Texto visible sobre fondo blanco
                    containerColor = Color.White // Fondo blanco del botón
                )
            ) {
                Text("Previous")
            }
            TextButton(
                onClick = { onIndexChange((currentIndex + 1) % artworksCount) },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = buttonTextColor, // Texto visible sobre fondo blanco
                    containerColor = Color.White // Fondo blanco del botón
                )
            ) {
                Text("Next")
            }
        }
    }
}


// DisplayInformation es el composable que muestra la información de la obra de arte seleccionada.
@Composable
fun DisplayInformation(
    artPiece: ArtPieceDetails,
    currentIndex: Int,
    artCount: Int,
    onIndexChange: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(36.dp)) // Añade un espacio adicional en la parte superior
        ArtPieceImage(artPiece.imageResource)
        GalleryNavigationRow(currentIndex, artCount, onIndexChange)
        ArtPieceText(title = artPiece.title, fontWeight = FontWeight.Bold)
        ArtPieceText(title = "by ${artPiece.creator}")
        ArtPieceText(title = "Year: ${artPiece.creationYear}")
    }
}

// ArtPieceImage muestra la imagen de la obra de arte seleccionada.
@Composable
fun ArtPieceImage(imageResource: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .clip(shape = MaterialTheme.shapes.medium)
            .background(color = Color.LightGray)
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

// ArtPieceText muestra el texto de la obra de arte seleccionada.
@Composable
fun ArtPieceText(title: String, fontWeight: FontWeight? = null) {
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = fontWeight
    )
}
// values devuelve una lista de detalles de las obras de arte.
fun values() = listOf(
    // Detalles de 'El Grito'.
    ArtPieceDetails(
        imageResource = R.drawable.el_grito,
        title = "El Grito",
        creator = "Edvard Munch",
        creationYear = "1843"
    ),
    // Detalles de 'La Joven de la Perla'.
    ArtPieceDetails(
        imageResource = R.drawable.joven_de_la_perla,
        title = "La Joven de la Perla",
        creator = "Johannes Vermeer",
        creationYear = "1665"
    )

)

// Data class que representa los detalles de una obra de arte.
data class ArtPieceDetails(
    val imageResource: Int,
    val title: String,
    val creator: String,
    val creationYear: String
)




