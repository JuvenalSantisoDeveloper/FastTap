package com.mustbear.app_fasttap.network;

import android.os.Bundle;

public interface NetworkConnections {

    public void onConnectionFailed();

    public void onConnected(Bundle bundle);

    public void onConnectionSuspended(int i);

}
