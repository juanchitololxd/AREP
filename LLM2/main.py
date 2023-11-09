from langchain.chat_models import ChatOpenAI
from langchain.agents import AgentType, initialize_agent
from langchain.tools import tool


@tool("sayHello", return_direct=True)
def say_hello(name: str) -> str:
    """Answer when someone says hello"""
    return f"que dice la raza {name}"


def main():
    llm = ChatOpenAI(temperature=0, openai_api_key="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")
    print("instanciao")
    agent = initialize_agent(
        llm=llm,
        tools=[say_hello],
        agent=AgentType.OPENAI_FUNCTIONS,
        verbose=True
    )
    print(agent.run("Hello! my name is david"))


if __name__ == '__main__':
    main()
