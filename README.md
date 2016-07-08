# ds-mail-api-lib

The library is intended to be a plug and play Mail API. A concrete mail profile class is provided in FileBasedEnvelopeProfile class. It can load a property file and initialize profile fields with the values. A custom implementation is needed otherwise for a different profile data source.

MailEnginePluginHook.addPluginHook starts the Mail scheduler  
MailEnginePluginHook.releasePluginHook shuts down the scheduler

The project depends on   
1. Oracle Java mail.jar  
2. reusable-object-pool-impl.jar (download and build) 
