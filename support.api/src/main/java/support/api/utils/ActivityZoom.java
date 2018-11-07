package support.api.utils;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import support.api.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ActivityZoom extends AppCompatActivity {

    public static Bitmap bitmap;
    private ImageView imageView;
    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_zoom);

        try {

            if (bitmap == null)
                throw new NullPointerException("Propriedade bitmap precisa ser setado antes de inicializa a activity");

            //Inicializa controle
            imageView = (ImageView) this.findViewById(R.id.imageView);

            //Attach a PhotoViewAttacher, which takes care of all of the zooming functionality.
            mAttacher = new PhotoViewAttacher(imageView);

            //Seta bitmap na imagem
            imageView.setImageBitmap(bitmap);

            //Atualiza adapter
            mAttacher.update();
        }
        catch (Exception err) {
            LogTrace.logCatch(this, this.getClass(), err, true);
        }
    }
}
