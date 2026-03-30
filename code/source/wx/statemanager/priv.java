package wx.statemanager;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.List;
import com.ibm.webmethods.is.statemgmt.Aquirer;
import com.ibm.webmethods.is.statemgmt.JobCollectionManager.ProviderConnectionFactory;
import com.ibm.webmethods.is.statemgmt.cloudstreams.AquirerForAllCSListeners;
import com.ibm.webmethods.is.statemgmt.connectors.AquirerForAllARTListeners;
import com.ibm.webmethods.is.statemgmt.providers.IgniteJobCoordinator.IgniteProvider;
import com.ibm.webmethods.is.statemgmt.providers.RedisJobCoordinator.RedisProvider;
import com.ibm.webmethods.is.statemgmt.providers.TerracottaJobCoordinator.TerracottaProvider;
import com.ibm.webmethods.is.statemgmt.scheduler.AquirerForAllSchedulers;
import com.ibm.webmethods.is.statemgmt.scheduler.SchedulerAquirer;
import com.ibm.webmethods.is.statemgmt.triggers.AquirerForAllTriggerJobs;
import com.ibm.webmethods.is.statemgmt.triggers.TriggerAquirer;
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




	public static final void aquireOwnershipForAllARTListeners (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(aquireOwnershipForAllARTListeners)>> ---
		// @sigtype java 3.5
		// [i] object:0:required stateCheckInterval
		// [o] record:1:required jobs
		String instanceName = ServerAPI.getServerName();
				
		try {
			ProviderConnectionFactory<com.ibm.webmethods.is.statemgmt.connectors.ListenerAquirer> p = determineProvider();
			artMgr = new AquirerForAllARTListeners(p, instanceName);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		// output
		
		List<IData> jobs = artMgr.getManagedJobs();
		
		new IDataMap(pipeline).put("jobs", jobs.toArray(new IData[jobs.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void aquireOwnershipForAllCloudStreamsListeners (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(aquireOwnershipForAllCloudStreamsListeners)>> ---
		// @sigtype java 3.5
		// [i] object:0:required stateCheckInterval
		// [o] record:1:required jobs
		String instanceName = ServerAPI.getServerName();
						
		try {
			ProviderConnectionFactory<com.ibm.webmethods.is.statemgmt.cloudstreams.ListenerAquirer> p = determineProvider();
			csMgr = new AquirerForAllCSListeners(p, instanceName);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		// output
		
		List<IData> jobs = csMgr.getManagedJobs();
		
		new IDataMap(pipeline).put("jobs", jobs.toArray(new IData[jobs.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void aquireOwnershipForAllTriggers (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(aquireOwnershipForAllTriggers)>> ---
		// @sigtype java 3.5
		// [i] object:0:required stateCheckInterval
		// [o] record:1:required jobs
		String instanceName = ServerAPI.getServerName();
			
		try {
			ProviderConnectionFactory<TriggerAquirer> p = determineProvider();
			 triggerMgr = new AquirerForAllTriggerJobs(p, instanceName);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		// output
		
		List<IData> jobs = triggerMgr.getManagedJobs();
		
		new IDataMap(pipeline).put("jobs", jobs.toArray(new IData[jobs.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void aquireOwnershipForScheduledJobs (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(aquireOwnershipForScheduledJobs)>> ---
		// @sigtype java 3.5
		// [o] record:1:required jobs
		String instanceName = ServerAPI.getServerName();
		
		try {
			ProviderConnectionFactory<SchedulerAquirer> p = determineProvider();
			 scheduleMgr = new AquirerForAllSchedulers(p, instanceName);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		// output
		
		List<IData> jobs = scheduleMgr.getManagedJobs();
		
		new IDataMap(pipeline).put("jobs", jobs.toArray(new IData[jobs.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void getARTJobs (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getARTJobs)>> ---
		// @sigtype java 3.5
		// [o] record:1:required jobs
		// [o] - field:0:required oid
		// [o] - field:0:required name
		// [o] - field:0:required type {"complex","repeat"}
		// [o] - object:0:required isOwner
		// [o] - object:0:required execState
		List<IData> jobs = artMgr.getManagedJobs();
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
		List<IData> jobs = scheduleMgr.getManagedJobs();
		new IDataMap(pipeline).put("jobs", jobs.toArray(new IData[jobs.size()]));
		// --- <<IS-END>> ---

                
	}



	public static final void shutdown (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(shutdown)>> ---
		// @sigtype java 3.5
		if (scheduleMgr != null)
			scheduleMgr.release();
		
		if (artMgr != null)
			artMgr.release();
		
		if (csMgr != null)
			csMgr.release();
		
		if (triggerMgr != null)
			triggerMgr.release();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	private static String ENV_VAR_REDIS_SERVER = "WM_STATEMGR_REDIS_SERVER";
	private static String ENV_VAR_REDIS_PORT = "WM_STATEMGR_REDIS_PORT";
	private static String ENV_VAR_IGNITE_ENDPOINTS = "WM_STATEMGR_IGNITE_ENDPOINTS";
	private static String ENV_VAR_TERRACOTTA_SERVER = "WM_STATEMGR_TERRACOTTA_SERVER_URI";
	//private static String ENV_STATE_CHECKER_INTERVAL = "WM_STATEMGR_CHECKER_INTERVAL"; // moved to JobChecker class
	
	private static AquirerForAllSchedulers scheduleMgr = null;
	private static AquirerForAllTriggerJobs triggerMgr = null;
	private static AquirerForAllARTListeners artMgr = null;
	private static AquirerForAllCSListeners csMgr = null;
		
	private static <T extends Aquirer> ProviderConnectionFactory<T> determineProvider() throws ServiceException {
		
		String terracottaServer = (String) System.getenv(ENV_VAR_TERRACOTTA_SERVER);	
		String redisServer = (String) System.getenv(ENV_VAR_REDIS_SERVER);		
		String igniteEndpoints = (String) System.getenv(ENV_VAR_IGNITE_ENDPOINTS);		
	
		ProviderConnectionFactory<T> provider = null;
		
		if (redisServer != null) {
			
			String redisPort = System.getenv(ENV_VAR_REDIS_PORT);		
	
			provider = new RedisProvider<T>(redisServer, redisPort != null ? Integer.parseInt(redisPort) : -1);
			
		} else if (igniteEndpoints != null) {
			
			String[] endpoints = igniteEndpoints.split(",");
			int timeoutInSeconds = 30;
			int heartbeatIntervalInMilliseconds = 30000;
			int retryCount = 3;
			
			provider = new IgniteProvider<T>(endpoints, timeoutInSeconds, heartbeatIntervalInMilliseconds, retryCount);
	
		} else if (terracottaServer != null) {
			terracottaServer = "terracotta://" + terracottaServer;
			//terracottaServer = "terracotta://localhost:9410";
			
			System.out.println("******** " + ENV_VAR_TERRACOTTA_SERVER + "=" + terracottaServer);
			
			provider = new TerracottaProvider<T>(terracottaServer);
			
		} else {
			throw new ServiceException("No valid provider properties given, please set them via the environment!!");
		}
		
		return provider;
	}
	// --- <<IS-END-SHARED>> ---
}

