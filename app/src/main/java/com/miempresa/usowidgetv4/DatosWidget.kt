package com.miempresa.usowidgetv4

import android.appwidget.AppWidgetManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_datos_widget.*

private var widgetId = 0
class DatosWidget : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_widget)

        val recibidowidget = intent
        val parametros = recibidowidget.extras
        if (parametros != null) {
            // se obtiene ID de widget que se esta configurando
            widgetId = parametros.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
        }

        // se establece un resultado por defecto cuando se pulse el boton
        // de Atras del telefono, este sera el mensaje mostrado)
        setResult(RESULT_CANCELED)

        btnAceptar.setOnClickListener(View.OnClickListener {
            val datos = getSharedPreferences( "DatosWidget", MODE_PRIVATE)
            // se apertura editor para guardar datos
            val editor = datos.edit()
            editor.putString("mensaje", txtEnviar.getText().toString())
            // aplica cambios
            editor.commit()

            // se actualiza el contenido del widget
            val notificarwidget = AppWidgetManager.getInstance(this)
            val usoClaseWidget = mi_widget()
            usoClaseWidget.actualizarWidget(this, notificarwidget, widgetId)

            // se devuelve como resultado: ACEPTAR (RESULT_OK)
            val resultado = Intent()
            resultado.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            setResult(RESULT_OK, resultado)
            finish()


        })

        btnCancelar.setOnClickListener(View.OnClickListener { finish() })

    }
}