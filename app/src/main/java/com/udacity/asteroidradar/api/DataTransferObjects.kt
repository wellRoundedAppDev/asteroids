
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.DataBaseAsteroid


@JsonClass(generateAdapter = true)
data class NetworkAsteroidContainer(val asteroids: List<Asteroid>)

@JsonClass(generateAdapter = true)
data class Asteroid(
 val id: Long,
 val codename: String,
 val closeApproachDate: String,
 val absoluteMagnitude: Double,
 val estimatedDiameter: Double,
 val relativeVelocity: Double,
 val distanceFromEarth: Double,
 val isPotentiallyHazardous: Boolean)

/**
 * Convert Network results to database objects
 */
fun NetworkAsteroidContainer.asDomainModel(): List<Asteroid> {
 return asteroids.map {
  Asteroid(
   id = it.id,
   codename = it.codename,
   closeApproachDate = it.closeApproachDate,
   absoluteMagnitude = it.absoluteMagnitude,
   estimatedDiameter = it.estimatedDiameter,
   relativeVelocity = it.relativeVelocity,
   distanceFromEarth = it.distanceFromEarth,
   isPotentiallyHazardous = it.isPotentiallyHazardous
  )
 }
}

fun NetworkAsteroidContainer.asDatabaseModel(): Array<DataBaseAsteroid> {
 return asteroids.map {
  DataBaseAsteroid(
   id = it.id,
   codename = it.codename,
   closeApproachDate = it.closeApproachDate,
   absoluteMagnitude = it.absoluteMagnitude,
   estimatedDiameter = it.estimatedDiameter,
   relativeVelocity = it.relativeVelocity,
   distanceFromEarth = it.distanceFromEarth,
   isPotentiallyHazardous = it.isPotentiallyHazardous)
 }.toTypedArray()

}