import javax.sound.midi.MetaMessage

fun main(){
    printMessage("Dicoding") { name: String -> "Hello $name"}
}
fun printMessage(name: String, message: (String) -> String){
    print(message(name))
}