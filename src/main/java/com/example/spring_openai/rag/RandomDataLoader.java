package com.example.spring_openai.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RandomDataLoader {
    private final VectorStore vectorStore;

    public RandomDataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadSentencesIntoVectorStore(){
        List<String> sentences = List.of(
                "The capital of France is Paris.",
                "The Amazon rainforest produces a significant portion of Earth's oxygen.",
                "The Great Wall of China being visible from space is a myth.",
                "Water boils at 100 degrees Celsius at sea level.",
                "The human brain contains approximately 86 billion neurons.",
                "The Pacific Ocean is the largest ocean on Earth.",
                "The speed of light is approximately 299,792 kilometers per second.",
                "Mount Everest is the tallest mountain above sea level.",
                "The Earth revolves around the Sun in about 365 days.",
                "Gravity keeps planets in orbit around stars.",

                "Docker containers share the host OS kernel.",
                "Kubernetes manages containerized applications at scale.",
                "Git is a distributed version control system.",
                "REST APIs use HTTP methods like GET and POST.",
                "JSON is commonly used for data exchange in APIs.",
                "Java is a statically typed programming language.",
                "Spring Boot simplifies Java application development.",
                "Microservices architecture breaks applications into small services.",
                "CI/CD pipelines automate build and deployment processes.",
                "Cloud computing provides on-demand computing resources.",

                "Regular exercise improves cardiovascular health.",
                "Drinking enough water helps maintain hydration.",
                "Sleep is essential for mental and physical recovery.",
                "A balanced diet includes proteins, fats, and carbohydrates.",
                "Smoking increases the risk of lung cancer.",
                "Vaccines help prevent infectious diseases.",
                "Mental health is as important as physical health.",
                "Stress can negatively affect the immune system.",
                "Diabetes affects blood sugar regulation.",
                "High blood pressure can lead to heart disease.",

                "Inflation reduces the purchasing power of money.",
                "Stocks represent ownership in a company.",
                "Diversification reduces investment risk.",
                "Supply and demand determine market prices.",
                "Profit is revenue minus expenses.",
                "A startup aims for rapid growth.",
                "Cash flow is critical for business sustainability.",
                "Interest rates influence borrowing costs.",
                "Cryptocurrencies use blockchain technology.",
                "A balance sheet shows assets and liabilities.",

                "Everyone is equal before the law.",
                "Contracts require mutual agreement between parties.",
                "Intellectual property protects creative works.",
                "Privacy laws regulate data collection.",
                "Ethical behavior builds trust in organizations.",
                "Corruption undermines public institutions.",
                "Freedom of speech has legal limitations.",
                "Laws vary between countries.",
                "Legal systems enforce rules in society.",
                "Justice aims to ensure fairness.",

                "Climate change is driven by greenhouse gas emissions.",
                "Renewable energy includes solar and wind power.",
                "Deforestation contributes to biodiversity loss.",
                "Recycling reduces waste in landfills.",
                "Pollution harms ecosystems and human health.",
                "Oceans absorb carbon dioxide from the atmosphere.",
                "Conservation protects endangered species.",
                "Sustainable development balances growth and environment.",
                "Fossil fuels are non-renewable resources.",
                "Global warming increases average temperatures.",

                "Education improves critical thinking skills.",
                "Online learning has grown rapidly in recent years.",
                "Teachers play a key role in student development.",
                "Exams evaluate knowledge and understanding.",
                "Lifelong learning is important in modern careers.",
                "Research helps expand knowledge.",
                "Libraries provide access to information.",
                "Collaboration enhances learning outcomes.",
                "Technology supports digital education.",
                "Reading improves vocabulary and comprehension.",

                "Atoms are the basic units of matter.",
                "Photosynthesis converts sunlight into energy.",
                "DNA carries genetic information.",
                "Evolution explains the diversity of life.",
                "The periodic table organizes chemical elements.",
                "Energy cannot be created or destroyed.",
                "Cells are the building blocks of life.",
                "The human body has multiple organ systems.",
                "Chemical reactions involve changes in substances.",
                "Physics studies matter and energy.",

                "If a train travels at 60 km per hour for 2 hours, it covers 120 km.",
                "If John is older than Mary and Mary is older than Sam, John is the oldest.",
                "A discounted price is lower than the original price.",
                "Saving money regularly increases financial stability.",
                "If demand increases and supply stays constant, prices rise.",
                "Learning daily improves skills over time.",
                "If a system fails, debugging helps identify the issue.",
                "Practice leads to better performance.",
                "Time management increases productivity.",
                "Breaking problems into smaller parts makes them easier to solve.",

                "I forgot my password and need to reset it.",
                "The server is down and users cannot access the application.",
                "Please deploy the latest version to production.",
                "The meeting has been rescheduled to tomorrow.",
                "I need help understanding this error message.",
                "Can you review my code before I merge it?",
                "The API response time is too slow.",
                "We need to scale the application to handle more users.",
                "The database connection is failing intermittently.",
                "Let us discuss the project requirements in detail."
        );
        List<Document> documentList = sentences.stream().map(Document::new).collect(Collectors.toList());
        vectorStore.add(documentList);
    }

}
