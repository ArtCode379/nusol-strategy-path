package nusol.management.nusolstrategypath.data.repository

import java.time.LocalTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import nusol.management.nusolstrategypath.data.model.ServiceModel

class ServiceRepository {
    private val commonFeatures = listOf(
        "Senior consultant facilitation",
        "Actionable written recommendations",
        "30-day implementation follow-up",
    )

    private val services = listOf(
        service(
            1, "Executive Strategy Session", "Strategic Planning", 420.0, 120,
            "Align leadership around a practical growth agenda and the decisions needed to deliver it.",
            "https://images.unsplash.com/photo-1552664730-d307ca884978?w=1400&q=85",
        ),
        service(
            2, "Organizational Structure Diagnostic", "People & Organization", 650.0, 180,
            "Reveal unclear accountability, duplicated work and structural barriers to better performance.",
            "https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=1400&q=85",
        ),
        service(
            3, "Business Process Audit", "Operational Excellence", 580.0, 180,
            "Map critical workflows, identify delays and create a prioritized efficiency roadmap.",
            "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?w=1400&q=85",
        ),
        service(
            4, "Growth Strategy Blueprint", "Strategic Planning", 890.0, 240,
            "Turn market evidence into clear growth choices, milestones and measurable outcomes.",
            "https://images.unsplash.com/photo-1531497865144-0464ef8fb9a9?w=1400&q=85",
        ),
        service(
            5, "Leadership Team Alignment", "People & Organization", 520.0, 120,
            "Strengthen leadership routines, decision rights and shared accountability across the team.",
            "https://images.unsplash.com/photo-1521737711867-e3b97375f902?w=1400&q=85",
        ),
        service(
            6, "Change Readiness Assessment", "Change Management", 480.0, 120,
            "Assess stakeholder readiness and create a focused plan for sustainable organizational change.",
            "https://images.unsplash.com/photo-1556761175-b413da4baf72?w=1400&q=85",
        ),
        service(
            7, "Performance Management Design", "People & Organization", 760.0, 180,
            "Build meaningful objectives, review rhythms and dashboards that support better decisions.",
            "https://images.unsplash.com/photo-1551288049-bebda4e38f71?w=1400&q=85",
        ),
        service(
            8, "Operating Model Review", "Operational Excellence", 940.0, 240,
            "Connect structure, governance, capabilities and processes to your strategic priorities.",
            "https://images.unsplash.com/photo-1553877522-43269d4ea984?w=1400&q=85",
        ),
        service(
            9, "Customer Journey Redesign", "Operational Excellence", 620.0, 180,
            "Reframe the end-to-end customer journey and remove friction from priority touchpoints.",
            "https://images.unsplash.com/photo-1556761175-4b46a572b786?w=1400&q=85",
        ),
        service(
            10, "Transformation Roadmap", "Change Management", 1100.0, 300,
            "Sequence major initiatives into an achievable transformation portfolio with clear ownership.",
            "https://images.unsplash.com/photo-1517245386807-bb43f82c33c4?w=1400&q=85",
        ),
    )

    private fun service(
        id: Int,
        name: String,
        category: String,
        price: Double,
        duration: Int,
        description: String,
        image: String,
    ): ServiceModel {
        return ServiceModel(
            id = id,
            name = name,
            description = description,
            price = price,
            availableTime = listOf(LocalTime.of(9, 0), LocalTime.of(11, 30), LocalTime.of(14, 0)),
            imageUrl = image,
            category = category,
            durationMinutes = duration,
            features = commonFeatures + "Tailored to your organization and industry",
        )
    }

    fun observeAll(): Flow<List<ServiceModel>> = flowOf(services)

    fun observeById(id: Int): Flow<ServiceModel?> = flowOf(getById(id))

    fun getById(id: Int): ServiceModel? = services.firstOrNull { it.id == id }
}
