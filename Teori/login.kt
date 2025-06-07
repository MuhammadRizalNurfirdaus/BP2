fun main() {
    val validUsername = "admin"
    val validPassword = "12345"

    while (true) {
        print("Masukkan Username: ")
        val username = readLine() ?: ""
        print("Masukkan Password: ")
        val password = readLine() ?: ""

        if (username == validUsername && password == validPassword) {
            println("Login berhasil. Selamat datang, $username!")
            break
        } else {
            println("Username atau password salah. Coba lagi.\n")
            continue
        }
    }
}
