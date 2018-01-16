cd C:\Users\Negan\Documents\AnonymousChat
mvn exec:java -D "exec.mainClass"="it.isislab.p2p.chat.TestShell"  -Dexec.args="%1" -Dexec.classpathScope=test
cmd /k