import static spark.Spark.*;

public class Forms {

    public static void main(String[] args) {
        // Configura a porta (pode mudar se precisar)
        port(4567);

        // Serve arquivos estáticos (CSS, imagens) da pasta resources/public
        staticFiles.location("/public");

        // Página inicial com formulário
        get("/", (req, res) -> {
            return htmlFormPage("");
        });

        // Recebe dados do formulário via POST
        post("/submit", (req, res) -> {
            String nome = req.queryParams("nome");
            String email = req.queryParams("email");
            String mensagem = req.queryParams("mensagem");

            // Pode validar ou salvar os dados aqui

            String resposta = "<p>Obrigado, <strong>" + escapeHtml(nome) + "</strong>, sua mensagem foi recebida!</p>";

            return htmlFormPage(resposta);
        });
    }

    // Função para evitar injeção HTML simples
    private static String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

    // Gera página HTML com formulário e mensagem de retorno
    private static String htmlFormPage(String mensagemRetorno) {
        return """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1" />
                <title>Contato - Do Pó ao Colapso</title>
                <link rel="stylesheet" href="/style.css" />
                <style>
                    body {
                        background-color: #121228;
                        color: #e0e6ff;
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                        margin: 0;
                        padding: 0;
                    }
                    header {
                        background-color: #202040;
                        padding: 1rem 2rem;
                        text-align: center;
                        box-shadow: 0 2px 10px rgba(0,0,0,0.8);
                    }
                    header h1 {
                        margin: 0;
                        font-size: 2.5rem;
                        color: #aabaff;
                        text-shadow: 2px 2px 5px #000033;
                    }
                    nav a {
                        color: #aabaff;
                        text-decoration: none;
                        margin: 0 1rem;
                        font-weight: 600;
                        transition: color 0.3s ease;
                    }
                    nav a:hover {
                        color: #d1d9ff;
                    }
                    main {
                        max-width: 600px;
                        margin: 3rem auto;
                        background-color: rgba(30, 30, 60, 0.85);
                        padding: 2rem 3rem;
                        border-radius: 20px;
                        box-shadow: 0 0 25px rgba(100, 120, 255, 0.7);
                    }
                    h2 {
                        text-align: center;
                        color: #d1d9ff;
                        font-size: 2.5rem;
                        margin-bottom: 1.5rem;
                    }
                    form {
                        display: flex;
                        flex-direction: column;
                    }
                    label {
                        margin: 0.8rem 0 0.3rem;
                        font-weight: 600;
                        color: #aabaff;
                    }
                    input, textarea {
                        padding: 0.7rem 1rem;
                        border-radius: 10px;
                        border: none;
                        font-size: 1.1rem;
                        outline: none;
                        resize: vertical;
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    }
                    input:focus, textarea:focus {
                        box-shadow: 0 0 8px #799eff;
                    }
                    button {
                        margin-top: 1.8rem;
                        padding: 1rem;
                        background-color: #5973ff;
                        border: none;
                        border-radius: 15px;
                        font-size: 1.3rem;
                        font-weight: 700;
                        color: white;
                        cursor: pointer;
                        transition: background-color 0.3s ease;
                    }
                    button:hover {
                        background-color: #7a94ff;
                    }
                    .mensagem-retorno {
                        margin-top: 2rem;
                        padding: 1rem;
                        background-color: #2a2a50;
                        border-radius: 15px;
                        text-align: center;
                        font-size: 1.15rem;
                        box-shadow: 0 0 12px #5f7eff;
                    }
                </style>
            </head>
            <body>
                <header>
                    <h1>Do Pó ao Colapso</h1>
                    <nav>
                        <a href="index.html">Início</a>
                        <a href="home.html">Home</a>
                        <a href="estrelas.html">Estrelas</a>
                        <a href="universo.html">Universo</a>
                        <a href="criacao.html">Criação</a>
                        <a href="/">Contato</a>
                    </nav>
                </header>
                <main>
                    <h2>Contato</h2>
                    <form action="/submit" method="POST">
                        <label for="nome">Nome:</label>
                        <input type="text" id="nome" name="nome" required />

                        <label for="email">Email:</label>
                        <input type="email" id="email" name="email" required />

                        <label for="mensagem">Mensagem:</label>
                        <textarea id="mensagem" name="mensagem" rows="5" required></textarea>

                        <button type="submit">Enviar</button>
                    </form>
                    %s
                </main>
            </body>
            </html>
            """.formatted(mensagemRetorno.isEmpty() ? "" : "<div class='mensagem-retorno'>" + mensagemRetorno + "</div>");
    }
}
