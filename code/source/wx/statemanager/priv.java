package wx.statemanager;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.List;
import com.ibm.webmethods.is.statemgmt.scheduler.AquirerForAllSchedulers;
import com.softwareag.util.IDataMap;
import com.wm.app.b2b.server.ServerAPI;
// --- <<IS-END-IMPORTS>> ---

public final class priv

{
	// ---( internal utility methods )---

	final static priv _instance = new priv();

	static priv _newInstance() { return new priv(); }

	static priv _cast(Object o) { return (priv)o; }

	// ---( server methods )---




	public static final void aquireOwnershipForScheduledJobs (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(aquireOwnershipForScheduledJobs)>> ---
		// @sigtype java 3.5
		// [i] object:0:required stateCheckInterval
		// [o] record:1:required jobs
		long checkIntervalLong;
		try {
		    checkIntervalLong = new IDataMap(pipeline).getAsInteger("stateCheckInterval");
		} catch (Exception e) {
			checkIntervalLong = -1;
		}
		
		String terracottaServer = (String) System.getenv(ENV_VAR_TERRACOTTA_SERVER);
		String instanceName = ServerAPI.getServerName();
		
		
		if (terracottaServer == null) {
			terracottaServer = "terracotta://localhost:9410";
		} else {
			terracottaServer = "terracotta://" + terracottaServer;
		}
		
		System.out.println("******** " + ENV_VAR_TERRACOTTA_SERVER + "=" + terracottaServer);
				
		try {
			 scheduleMgr = new AquirerForAllSchedulers(terracottaServer, instanceName);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		// output
		
		List<IData> jobs = scheduleMgr.getScheduledJobs();
		
		new IDataMap(pipeline).put("jobs", jobs.toArray(new IData[jobs.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void getScheduledJobs (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getScheduledJobs)>> ---
		// @sigtype java 3.5
		// [o] record:1:required jobs
		// [o] - field:0:required oid
		// [o] - field:0:required name
		// [o] - field:0:required type {"complex","repeat"}
		// [o] - object:0:required isOwner
		// [o] - object:0:required execState
		List<IData> jobs = scheduleMgr.getScheduledJobs();
		new IDataMap(pipeline).put("jobs", jobs.toArray(new IData[jobs.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void shutdown (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(shutdown)>> ---
		// @sigtype java 3.5
		scheduleMgr.release();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	
	private static String ENV_VAR_TERRACOTTA_SERVER = "WM_TERRACOTTA_SERVER_URI";
	
	private static AquirerForAllSchedulers scheduleMgr = null;
	// --- <<IS-END-SHARED>> ---
}

