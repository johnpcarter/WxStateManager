app.service('myserv', function() {
          this.getServiceList = function () {
    return [];
}
this.getISEndpoint = function() { 
 return 'http://localhost:5555/';
}
this.getAPIList = function() { 
 return [];
}
this.getCreatedTime = function() { 
 return "08-10-2025 11:52:44 CEST";
}
this.getPackageInfo = function(){
 return{"packageName":"WxStateManager","displayName":"State Manager for MSR","status":"Prototype","tags":["state","terracotta","scheduler","listener","trigger"],"createdDate":"30-09-2025 15:10:57 CEST","version":"1.0","buildNumber":"10","description":"Provides state management (via a terracotta server) when running replicas, to ensure assets\nsuch as scheduler, listeners and message triggers are only enabled\nin a single instance to avoid duplicate processing."};
}
});
