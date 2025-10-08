package wx.statemanager;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.ArrayList;
import java.util.List;
import com.ibm.webmethods.is.statemgmt.ListenerCoordinator;
import com.ibm.webmethods.is.statemgmt.scheduler.AquirerForAllScheduledJobs;
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
		String checkInterval = (String) System.getenv(ENV_VAR_STATE_CHECK_INTERVAL);
		String instanceName = ServerAPI.getServerName();
		
		if (terracottaServer == null) {
			terracottaServer = "terracotta://localhost:9410";
		} else {
			terracottaServer = "terracotta://" + terracottaServer;
		}
		
		if (checkIntervalLong == -1 && checkInterval != null) {
			try { checkIntervalLong = Long.parseLong(checkInterval); } catch(Exception e) {}
		}
				
		try {
			 scheduleMgr = new AquirerForAllScheduledJobs(terracottaServer, instanceName, checkIntervalLong);
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
	private static String ENV_VAR_STATE_CHECK_INTERVAL = "WM_STATE_CHECKER_INTERVAL";
	
	private static AquirerForAllScheduledJobs scheduleMgr = null;
	// --- <<IS-END-SHARED>> ---
}

