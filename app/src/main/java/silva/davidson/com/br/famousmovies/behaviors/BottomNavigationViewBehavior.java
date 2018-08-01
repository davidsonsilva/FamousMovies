package silva.davidson.com.br.famousmovies.behaviors;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.view.View;

public class BottomNavigationViewBehavior extends CoordinatorLayout.Behavior<BottomNavigationView> {

    private int height;

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, BottomNavigationView child,
                                 int layoutDirection) {
        height = child.getHeight();
        return super.onLayoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       BottomNavigationView child, @NonNull
                                               View directTargetChild, @NonNull View target,
                                       int axes, int type)
    {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child,
               @NonNull View target, int dxConsumed, int dyConsumed,
               int dxUnconsumed, int dyUnconsumed, 
                @ViewCompat.NestedScrollType int type)
    {
       if (dyConsumed > 0) {
           slideDown(child);
       } else if (dyConsumed < 0) {
           slideUp(child);
       }
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, BottomNavigationView child, View dependency) {
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

    private void slideUp(BottomNavigationView child) {
        child.clearAnimation();
        child.animate().translationY(0).setDuration(200);
    }

    private void slideDown(BottomNavigationView child) {
        child.clearAnimation();
        child.animate().translationY(height).setDuration(200);
    }
}