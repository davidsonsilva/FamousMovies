package silva.davidson.com.br.famousmovies.behaviors;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;

public final class BottomNavigationBehavior extends CoordinatorLayout.Behavior {

   public boolean layoutDependsOn(@Nullable CoordinatorLayout parent, @NonNull View child, @Nullable View dependency) {
      if (dependency instanceof Snackbar.SnackbarLayout) {
         this.updateSnackbar(child, (Snackbar.SnackbarLayout)dependency);
      }
      return super.layoutDependsOn(parent, child, dependency);
   }

   private final void updateSnackbar(View child, Snackbar.SnackbarLayout snackbarLayout) {
      if (snackbarLayout.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
         android.view.ViewGroup.LayoutParams var10000 = snackbarLayout.getLayoutParams();
         if (var10000 == null) {
            //throw new TypeCastException("null cannot be cast to non-null type android.support.design.widget.CoordinatorLayout.LayoutParams");
         }

         CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)var10000;
         params.setAnchorId(child.getId());
         params.anchorGravity = 48;
         params.gravity = 48;
         snackbarLayout.setLayoutParams((android.view.ViewGroup.LayoutParams)params);
      }

   }

   public BottomNavigationBehavior(@NonNull Context context, @NonNull AttributeSet attrs) {
      super(context, attrs);
   }
}