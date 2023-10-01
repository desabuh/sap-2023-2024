package mvc_02_dist_rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteModelObserver extends Remote {

	//è necessario estendere il metodo di notifica alla view remota fornendo anche lo stato aggiornato del model
	// (infatti la view remota non potrà invocare il getState() localmente sul model, trovandosi appunto in remoto)
	void notifyModelUpdated(int state) throws RemoteException;
}
