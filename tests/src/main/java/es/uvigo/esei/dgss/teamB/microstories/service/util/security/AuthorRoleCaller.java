package es.uvigo.esei.dgss.teamB.microstories.service.util.security;

import java.util.function.Supplier;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.Stateless;

@Stateless(name = "author-caller")
@RunAs("AUTHOR")
@PermitAll
public class AuthorRoleCaller implements RoleCaller {
	
	public <V> V call(Supplier<V> supplier) {
		return supplier.get();
	}
	
	public void run(Runnable run) {
		run.run();
	}
	
	public <V> V throwingCall(ThrowingSupplier<V> supplier) throws Throwable {
		return supplier.get();
	}
	
	public void throwingRun(ThrowingRunnable run) throws Throwable{
		run.run();
	}
}
